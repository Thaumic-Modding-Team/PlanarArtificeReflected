package mod.emt.planarartifice.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

public class TilePassableGlass extends TileEntity implements IAspectContainer {
    protected AspectList aspectList = new AspectList();

    @Override
    public void readFromNBT(@NotNull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.aspectList.readFromNBT(compound);
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.aspectList.writeToNBT(compound);
        return compound;
    }

    public void addAspect(Aspect aspect) {
        this.aspectList.add(aspect, 1);
        this.markDirty();
    }

    public void clearAspects() {
        this.aspectList.aspects.clear();
        this.markDirty();
    }

    @Override
    public AspectList getAspects() {
        if(this.aspectList != null && this.aspectList.size() > 0) {
            return this.aspectList;
        }
        return null;
    }

    @Override
    public void setAspects(AspectList aspectList) {

    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return false;
    }

    @Override
    public int addToContainer(Aspect aspect, int i) {
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int i) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int i) {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
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
