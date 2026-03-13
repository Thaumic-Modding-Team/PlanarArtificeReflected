package mod.emt.planarartifice.block.essentia;

import com.invadermonky.thaumicapi.api.block.ISmelterVent;
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

public class BlockSmelterVentPA extends BlockSmelterAddon implements ISmelterVent {
    public static final AxisAlignedBB[] VENT_AABBS = new AxisAlignedBB[] {
        new AxisAlignedBB(0.1875, 0.1875, 0.5, 0.8125, 0.8125, 1.0),    //South
        new AxisAlignedBB(0, 0.1875, 0.1875, 0.5, 0.8125, 0.8125),      //West
        new AxisAlignedBB(0.1875, 0.1875, 0, 0.8125, 0.8125, 0.5),      //North
        new AxisAlignedBB(0.5, 0.1875, 0.1875, 1.0, 0.8125, 0.8125)     //East
    };
    protected float filterChance;

    public BlockSmelterVentPA(String name, float filterChance) {
        super(name);
        this.filterChance = filterChance;
    }

    @Override
    public @NotNull AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return VENT_AABBS[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public boolean canVentSmelter(World world, BlockPos pos, IBlockState state, @Nullable TileEntity smelter) {
        EnumFacing facing = state.getValue(FACING);
        return smelter != null && smelter == world.getTileEntity(pos.offset(facing));
    }

    @Override
    public float getFluxFilterChance(World world, BlockPos pos, IBlockState state, @Nullable TileEntity smelter) {
        return this.filterChance;
    }
}
