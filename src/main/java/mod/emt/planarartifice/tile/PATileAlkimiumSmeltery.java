package mod.emt.planarartifice.tile;

import com.invadermonky.thaumicapi.api.block.ISmelterAuxiliary;
import com.invadermonky.thaumicapi.api.block.ISmelterVent;
import mod.emt.planarartifice.block.PABlockAlkimiumSmeltery;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.essentia.BlockSmelter;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.utils.BlockStateUtils;
import thaumcraft.common.tiles.essentia.TileAlembic;
import thaumcraft.common.tiles.essentia.TileSmelter;

import java.lang.reflect.Method;

public class PATileAlkimiumSmeltery extends TileSmelter {
    boolean speedBoost = false;
    int count = 0;
    int bellows = -1;

    public static Method processAlembics = null;

    @Override
    public void update() {
        boolean flag = this.furnaceBurnTime > 0;
        boolean flag1 = false;
        this.count++;
        if (this.furnaceBurnTime > 0) {
            this.furnaceBurnTime--;
        }
        if (this.world != null) {
            if (this.bellows < 0)
                this.checkNeighbours();

            int speed = (int) (this.getSpeed() * (this.speedBoost ? 0.8 : 1));

            if (this.count % speed == 0 && this.aspects.size() > 0) {
                for (Aspect aspect : this.aspects.getAspects()) {
                    if (this.aspects.getAmount(aspect) <= 0 || !this.processAlembics(this.getPos(), aspect))
                        continue;
                    this.takeFromContainer(aspect, 1);
                    break;
                }

                for (EnumFacing face : EnumFacing.HORIZONTALS) {
                    IBlockState aux = this.world.getBlockState(this.getPos().offset(face));
                    if (!(aux.getBlock() instanceof ISmelterAuxiliary) || BlockStateUtils.getFacing(aux) != face.getOpposite())
                        continue;
                    for (Aspect aspect : this.aspects.getAspects()) {
                        if (this.aspects.getAmount(aspect) <= 0 || !processAlembics(this.getPos().offset(face), aspect))
                            continue;
                        this.takeFromContainer(aspect, 1);
                    }
                }
            }

            if (this.furnaceBurnTime == 0) {
                if (this.canSmelt()) {
                    this.currentItemBurnTime = this.furnaceBurnTime = TileEntityFurnace
                            .getItemBurnTime(this.getStackInSlot(1));
                    if (this.furnaceBurnTime > 0) {
                        BlockSmelter.setFurnaceState(this.world, this.getPos(), true);
                        flag1 = true;
                        this.speedBoost = false;
                        if (this.getStackInSlot(1) != ItemStack.EMPTY) {
                            if (this.getStackInSlot(1).isItemEqual(new ItemStack(ItemsTC.alumentum))) {
                                this.speedBoost = true;
                            }
                            this.getStackInSlot(1).shrink(1);
                            if (this.getStackInSlot(1).getCount() == 0) {
                                this.setInventorySlotContents(1,
                                        this.getStackInSlot(1).getItem().getContainerItem(this.getStackInSlot(1)));
                            }
                        }
                    } else {
                        BlockSmelter.setFurnaceState(this.world, this.getPos(), false);
                    }
                } else {
                    BlockSmelter.setFurnaceState(this.world, this.getPos(), false);
                }
            }
            if (BlockStateUtils.isEnabled(this.getBlockMetadata()) && this.canSmelt()) {
                this.furnaceCookTime++;
                if (this.furnaceCookTime >= this.smeltTime) {
                    this.furnaceCookTime = 0;
                    this.smeltItem();
                    flag1 = true;
                }
            } else {
                this.furnaceCookTime = 0;
            }
            if (flag != this.furnaceBurnTime > 0) {
                flag1 = true;
            }
        }
        if (flag1) {
            this.markDirty();
        }
    }

    public boolean canSmelt() {
        if (this.getStackInSlot(0).isEmpty()) {
            return false;
        }
        AspectList al = ThaumcraftCraftingManager.getObjectTags(this.getStackInSlot(0));
        if (al == null || al.size() == 0) {
            return false;
        }
        int vs = al.visSize();
        if (vs > this.getCapacity() - this.vis) {
            return false;
        }
        this.smeltTime = (int) (vs * 2 * (1.0f - 0.125f * this.bellows));
        return true;
    }

    @Override
    public void smeltItem() {
        if (this.canSmelt()) {
            int flux = 0;
            AspectList al = ThaumcraftCraftingManager.getObjectTags(this.getStackInSlot(0));
            for (Aspect a : al.getAspects()) {
                if (this.getEfficiency() < 1.0f) {
                    int qq = al.getAmount(a);
                    for (int q = 0; q < qq; ++q) {
                        if (this.world.rand.nextFloat() <= (a == Aspect.FLUX ? this.getEfficiency() * 0.66f
                                : this.getEfficiency()))
                            continue;
                        al.reduce(a, 1);
                        flux++;
                    }
                } else if (this.getEfficiency() > 1.0F) {
                    int qq = al.getAmount(a);
                    for (int q = 0; q < qq; ++q) {
                        if (this.world.rand.nextFloat()
                                + 1 >= (a == Aspect.FLUX ? this.getEfficiency() * 0.66f : this.getEfficiency()))
                            continue;
                        al.add(a, 1);
                    }
                }

                this.aspects.add(a, al.getAmount(a));
            }
            if (flux > 0) {
                int pp = 0;
                block2:
                for (int c = 0; c < flux; ++c) {
                    for (EnumFacing face : EnumFacing.HORIZONTALS) {
                        IBlockState vent = this.world.getBlockState(this.getPos().offset(face));
                        if (!(vent.getBlock() instanceof ISmelterVent)
                                || BlockStateUtils.getFacing(vent) != face.getOpposite()
                                || this.world.rand.nextFloat() >= 0.333)
                            continue;
                        this.world.addBlockEvent(this.getPos(), this.getBlockType(), 1, face.getOpposite().ordinal());
                        continue block2;
                    }
                    ++pp;
                }
                AuraHelper.polluteAura(this.getWorld(), this.getPos(), pp, true);
            }
            this.vis = this.aspects.visSize();
            this.getStackInSlot(0).shrink(1);
            if (this.getStackInSlot(0).getCount() <= 0) {
                this.setInventorySlotContents(0, ItemStack.EMPTY);
            }
        }
    }

    public boolean processAlembics(BlockPos pos, Aspect a) {
        try {
            if (processAlembics == null) {
                processAlembics = TileAlembic.class.getDeclaredMethod("processAlembics", World.class, BlockPos.class, Aspect.class);
                processAlembics.setAccessible(true);
            }
            return (boolean) processAlembics.invoke(null, this.getWorld(), pos, a);
        } catch (Exception e) {
            //PlanarArtifice.LOGGER.error(e);
            return false;
        }
    }

    public int getSpeed() {
        Block b = world.getBlockState(getPos()).getBlock();
        return b instanceof PABlockAlkimiumSmeltery ? ((PABlockAlkimiumSmeltery) b).getSpeed() : 20;
    }

    public float getEfficiency() {
        Block b = world.getBlockState(getPos()).getBlock();
        return b instanceof PABlockAlkimiumSmeltery ? ((PABlockAlkimiumSmeltery) b).getEfficiency() : 20;
    }

    public int getCapacity() {
        Block b = world.getBlockState(getPos()).getBlock();
        return b instanceof PABlockAlkimiumSmeltery ? ((PABlockAlkimiumSmeltery) b).getCapacity() : 375;
    }

    @Override
    public int getVisScaled(int par1) {
        return vis * par1 / this.getCapacity();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound) {
        super.readFromNBT(nbtCompound);
        this.speedBoost = nbtCompound.getBoolean("speedBoost");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtCompound) {
        nbtCompound = super.writeToNBT(nbtCompound);
        nbtCompound.setBoolean("speedBoost", this.speedBoost);
        return nbtCompound;
    }
}
