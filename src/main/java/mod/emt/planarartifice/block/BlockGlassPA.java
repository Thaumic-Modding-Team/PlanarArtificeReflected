package mod.emt.planarartifice.block;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class BlockGlassPA extends BlockGlass {
    protected Predicate<Entity> collisionPredicate;

    public BlockGlassPA(String name) {
        super(Material.GLASS, false);
        this.setRegistryName(PlanarArtifice.MOD_ID, name);
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
        this.setHardness(0.3f);
        this.setSoundType(SoundType.GLASS);
    }

    public Block setCollisionPredicate(Predicate<Entity> collisionPredicate) {
        this.collisionPredicate = collisionPredicate;
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
}
