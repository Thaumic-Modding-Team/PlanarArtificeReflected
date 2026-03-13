package mod.emt.planarartifice.block.essentia;

import com.invadermonky.thaumicapi.api.block.ISmelterAuxiliary;
import mod.emt.planarartifice.block.base.BlockSmelterAddon;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockSmelterAuxiliaryPA extends BlockSmelterAddon implements ISmelterAuxiliary {
    public static final AxisAlignedBB[] AUX_AABBS = new AxisAlignedBB[] {
            new AxisAlignedBB(0.0625, 0.0625, 0.25, 0.9375, 1.0, 1.0),    //South
            new AxisAlignedBB(0, 0.0625, 0.0625, 0.75, 1.0, 0.9375),      //West
            new AxisAlignedBB(0.0625, 0.0625, 0, 0.9375, 1.0, 0.75),      //North
            new AxisAlignedBB(0.25, 0.0625, 0.0625, 1.0, 1.0, 0.9375)     //East
    };
    protected int bonusOperations;

    public BlockSmelterAuxiliaryPA(String name, int bonusOperations) {
        super(name);
        this.bonusOperations = bonusOperations;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull AxisAlignedBB getBoundingBox(IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return AUX_AABBS[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public boolean canBoostSmelter(World world, BlockPos pos, IBlockState state, @Nullable TileEntity smelter) {
        EnumFacing facing = state.getValue(FACING);
        return smelter != null && smelter == world.getTileEntity(pos.offset(facing));
    }

    @Override
    public int getBonusOperations(World world, BlockPos pos, IBlockState state, @Nullable TileEntity smelter) {
        return this.bonusOperations;
    }
}
