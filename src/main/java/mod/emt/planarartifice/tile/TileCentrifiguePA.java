package mod.emt.planarartifice.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.lib.SoundsTC;

public class TileCentrifiguePA extends TileEntity implements ITickable, IEssentiaTransport, IAspectContainer {
    public Aspect aspectIn;
    public AspectList aspectsOut = new AspectList();
    public int progress;
    public int progressMax = 20;
    public float rotationSpeed = 0;
    public float rotation = 0;

    @Override
    public void readFromNBT(@NotNull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.aspectIn = Aspect.getAspect(compound.getString("aspectIn"));
        this.aspectsOut.readFromNBT(compound, "aspectsOut");
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(!this.isInputEmpty()) {
            compound.setString("aspectIn", this.aspectIn.getTag());
        }
        this.aspectsOut.writeToNBT(compound, "aspectsOut");
        return compound;
    }

    @Override
    public void update() {
        boolean did = false;
        boolean isPowered = this.isPowered();
        if(!this.world.isRemote) {
            if(!isPowered) {
                did |= this.drawEssentia();
                did |= this.processEssentia();
            }
        } else {
            if(!this.isInputEmpty() && !isPowered && this.rotationSpeed < 20.0f) {
                this.rotationSpeed += 2.0f;
            }
            if((this.isInputEmpty() || isPowered) && this.rotationSpeed > 0) {
                this.rotationSpeed -= Math.min(0.5f, this.rotationSpeed);
            }

            int pr = (int) this.rotation;
            this.rotation += rotationSpeed;
            if(this.rotation % 180.f <= 20.0f && pr % 180 >= 160 && this.rotationSpeed > 0) {
                this.world.playSound(
                        (double)this.getPos().getX() + (double)0.5F,
                        (double)this.getPos().getY() + (double)0.5F,
                        (double)this.getPos().getZ() + (double)0.5F,
                        SoundsTC.pump, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
        }

        if(did) {
            this.markDirty();
        }
    }

    public boolean drawEssentia() {
        if(this.isInputEmpty() && this.isOutputEmpty()) {
            TileEntity tile = this.world.getTileEntity(this.pos.offset(EnumFacing.DOWN));
            if (tile instanceof IEssentiaTransport) {
                IEssentiaTransport transport = (IEssentiaTransport) tile;
                if (transport.isConnectable(EnumFacing.UP) && transport.canOutputTo(EnumFacing.UP) && this.canPullFromTransport(transport)) {
                    Aspect tileAspect = null;
                    if (transport.getEssentiaAmount(EnumFacing.UP) > 0) {
                        tileAspect = transport.getEssentiaType(EnumFacing.UP);
                    }

                    if (tileAspect != null && transport.takeEssentia(tileAspect, 1, EnumFacing.UP) == 1) {
                        this.aspectIn = tileAspect;
                        this.progress = this.progressMax;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canPullFromTransport(IEssentiaTransport transport) {
        return transport.getSuctionAmount(EnumFacing.UP) < this.getSuctionAmount(EnumFacing.DOWN)
                && transport.getMinimumSuction() <= this.getSuctionAmount(EnumFacing.DOWN);
    }

    public boolean processEssentia() {
        if(this.isInputEmpty()) {
            if(this.progress > 0) {
                progress = 0;
                return true;
            }
        } else {
            if(this.progress > 0) {
                this.progress--;
            } else {
                Aspect[] components = this.aspectIn.getComponents();
                if(components != null) {
                    for(Aspect aspect : components) {
                        this.aspectsOut.add(aspect, 1);
                    }
                } else {
                    this.aspectsOut.add(this.aspectIn, 1);
                    this.progress = this.progressMax;
                }
                this.aspectIn = null;
            }
            return true;
        }
        return false;
    }

    public boolean isPowered() {
        return this.world.isBlockPowered(this.pos);
    }

    public boolean isInputEmpty() {
        return this.aspectIn == null;
    }

    public boolean isOutputEmpty() {
        return this.aspectsOut == null || this.aspectsOut.size() == 0;
    }

    @Override
    public AspectList getAspects() {
        return this.aspectsOut;
    }

    @Override
    public void setAspects(AspectList aspectList) {}

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return true;
    }

    @Override
    public int addToContainer(Aspect aspect, int amount) {
        if(amount > 0 && this.isOutputEmpty()) {
            this.aspectsOut.add(aspect, 1);
            this.markDirty();
            amount--;
        }
        return amount;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int amount) {
        if(!this.isOutputEmpty() && this.getAspects().aspects.containsKey(aspect)) {
            this.aspectsOut.remove(aspect, 1);
            this.markDirty();
            return true;
        }
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int amount) {
        return amount == 1 && this.getAspects().aspects.containsKey(aspect);
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        for(Aspect aspect : aspectList.getAspects()) {
            if(this.getAspects().aspects.containsKey(aspect)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return this.getAspects().getAmount(aspect);
    }

    @Override
    public boolean isConnectable(EnumFacing face) {
        return face == EnumFacing.UP || face == EnumFacing.DOWN;
    }

    @Override
    public boolean canInputFrom(EnumFacing face) {
        return face == EnumFacing.DOWN;
    }

    @Override
    public boolean canOutputTo(EnumFacing face) {
        return face == EnumFacing.UP;
    }

    @Override
    public void setSuction(Aspect aspect, int i) {}

    @Override
    public Aspect getSuctionType(EnumFacing enumFacing) {
        return null;
    }

    @Override
    public int getSuctionAmount(EnumFacing face) {
        if(face == EnumFacing.DOWN && !this.isPowered()) {
            return this.aspectIn == null ? 128 : 64;
        }
        return 0;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, EnumFacing face) {
        return this.canOutputTo(face) && this.takeFromContainer(aspect, amount) ? amount : 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, EnumFacing face) {
        if(this.isInputEmpty()) {
            this.aspectIn = aspect;
            this.progress = 20;
            this.markDirty();
            return 1;
        }
        return 0;
    }

    @Override
    public Aspect getEssentiaType(EnumFacing face) {
        return this.aspectsOut.getAspects().length > 0 ? this.aspectsOut.getAspects()[0] : null;
    }

    @Override
    public int getEssentiaAmount(EnumFacing face) {
        return !this.isOutputEmpty() ? 1 : 0;
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        IBlockState state = this.world.getBlockState(this.pos);
        this.world.notifyBlockUpdate(this.pos, state, state, 2);
        this.world.markAndNotifyBlock(this.pos, this.world.getChunk(this.pos), state, state, 3);
    }

    @Override
    public @Nullable SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public @NotNull NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(@NotNull NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }
}
