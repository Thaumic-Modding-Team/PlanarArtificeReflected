package mod.emt.planarartifice.block.glass;

import mod.emt.planarartifice.tile.TilePassableGlass;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.resources.ItemCrystalEssence;

import java.util.List;


public class BlockGlassPassable extends BlockGlassPA implements ITileEntityProvider {
    public BlockGlassPassable(String name) {
        super(name);
    }

    @Override
    public void addCollisionBoxToList(@NotNull IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull AxisAlignedBB entityBox, @NotNull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (!this.canEntityPass(worldIn, pos, entityIn)) {
            super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        }
    }

    @Override
    public @Nullable PathNodeType getAiPathNodeType(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @Nullable EntityLiving entity) {
        return this.canEntityPass(world, pos, entity) ? PathNodeType.OPEN : super.getAiPathNodeType(state, world, pos, entity);
    }

    public boolean canEntityPass(IBlockAccess world, BlockPos pos, Entity entity) {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TilePassableGlass && entity != null) {
            AspectList tileAspects = ((TilePassableGlass) tile).getAspects();
            AspectList entityAspects = AspectHelper.getEntityAspects(entity);
            if (tileAspects != null && entityAspects != null) {
                for(Aspect aspect : tileAspects.getAspects()) {
                    if(!entityAspects.aspects.containsKey(aspect)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TilePassableGlass) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if (heldItem.isEmpty() && playerIn.isSneaking()) {
                if(!worldIn.isRemote)
                    ((TilePassableGlass) tile).clearAspects();
                return true;
            } else if (!heldItem.isEmpty() && heldItem.getItem() instanceof ItemCrystalEssence) {
                AspectList aspectList = ((ItemCrystalEssence) heldItem.getItem()).getAspects(heldItem);
                if(aspectList != null && aspectList.size() == 1) {
                    if(!worldIn.isRemote)
                        ((TilePassableGlass) tile).addAspect(aspectList.getAspects()[0]);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Override
    public @Nullable TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        return new TilePassableGlass();
    }

    @Override
    public void breakBlock(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void harvestBlock(@NotNull World worldIn, @NotNull EntityPlayer player, @NotNull BlockPos pos, @NotNull IBlockState state, @Nullable TileEntity te, @NotNull ItemStack stack) {
        if (te instanceof IWorldNameable && ((IWorldNameable)te).hasCustomName()) {
            player.addStat(StatList.getBlockStats(this));
            player.addExhaustion(0.005F);

            if (worldIn.isRemote) {
                return;
            }

            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            Item item = this.getItemDropped(state, worldIn.rand, i);

            if (item == Items.AIR) {
                return;
            }

            ItemStack itemstack = new ItemStack(item, this.quantityDropped(worldIn.rand));
            itemstack.setStackDisplayName(((IWorldNameable)te).getName());
            spawnAsEntity(worldIn, pos, itemstack);
        } else {
            super.harvestBlock(worldIn, player, pos, state, null, stack);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean eventReceived(@NotNull IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }
}
