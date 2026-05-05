package mod.emt.planarartifice.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.NotNull;

public class BlockGlassRedstone extends BlockGlassPA {
    public BlockGlassRedstone(String name) {
        super(name);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getWeakPower(@NotNull IBlockState blockState, @NotNull IBlockAccess blockAccess, @NotNull BlockPos pos, @NotNull EnumFacing side) {
        return 15;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canProvidePower(@NotNull IBlockState state) {
        return true;
    }
}
