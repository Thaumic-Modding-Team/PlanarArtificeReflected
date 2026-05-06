package mod.emt.planarartifice.block.glass;

import mod.emt.planarartifice.block.base.BlockPA;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class BlockGlassPA extends BlockPA {
    protected Predicate<Entity> collisionPredicate;
    protected BlockRenderLayer renderLayer = BlockRenderLayer.CUTOUT;

    public BlockGlassPA(String name) {
        super(name, Material.GLASS);
        this.setHardness(0.3f);
        this.setSoundType(SoundType.GLASS);
    }

    public Block setCollisionPredicate(Predicate<Entity> collisionPredicate) {
        this.collisionPredicate = collisionPredicate;
        return this;
    }

    public Block setRenderLayer(BlockRenderLayer renderLayer) {
        this.renderLayer = renderLayer;
        return this;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(@NotNull IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull AxisAlignedBB entityBox, @NotNull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (this.collisionPredicate == null || !this.collisionPredicate.test(entityIn)) {
            super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        }
    }

    @Override
    public @Nullable PathNodeType getAiPathNodeType(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @Nullable EntityLiving entity) {
        if(this.collisionPredicate != null && this.collisionPredicate.test(entity)) {
            return PathNodeType.OPEN;
        } else {
            return super.getAiPathNodeType(state, world, pos, entity);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    public int quantityDropped(@NotNull Random random) {
        return 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public @NotNull BlockRenderLayer getRenderLayer() {
        return this.renderLayer;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldSideBeRendered(@NotNull IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, @NotNull EnumFacing side) {
        IBlockState state = blockAccess.getBlockState(pos.offset(side));
        return state != blockState || state.getBlock() != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}
