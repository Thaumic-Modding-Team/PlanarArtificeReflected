package mod.emt.planarartifice.block.essentia;

import com.invadermonky.thaumicapi.api.tile.AbstractTileEssentiaSmelter;
import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.block.base.BlockContainerPA;
import mod.emt.planarartifice.registry.ModGuiHandlerPA;
import mod.emt.planarartifice.tile.TileSmelterPA;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aura.AuraHelper;

import java.util.Random;

public class BlockSmelterPA extends BlockContainerPA {
    public static PropertyDirection FACING = BlockHorizontal.FACING;
    public static PropertyBool ENABLED = PropertyBool.create("enabled");

    private final int transferSpeed;
    private final float efficiency;
    private final int essentiaCapacity;

    public BlockSmelterPA(String name, int transferSpeed, float efficiency, int essentiaCapacity) {
        super(name, Material.IRON, MapColor.GREEN);
        this.setHardness(4.0f);
        this.setResistance(6.0f);
        this.setSoundType(SoundType.METAL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ENABLED, false));
        this.transferSpeed = transferSpeed;
        this.efficiency = efficiency;
        this.essentiaCapacity = essentiaCapacity;
    }

    public int getTransferSpeed() {
        return this.transferSpeed;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public int getEssentiaCapacity() {
        return this.essentiaCapacity;
    }

    @Override
    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileSmelterPA) {
            playerIn.openGui(PlanarArtifice.instance, ModGuiHandlerPA.ID_ALKIMIUM_SMELTERY, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    @Override
    public @NotNull IBlockState getStateForPlacement(@NotNull World world, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, @NotNull EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ENABLED, false);
    }

    @Override
    public void breakBlock(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(!worldIn.isRemote && tile instanceof AbstractTileEssentiaSmelter) {
            int essentia = ((AbstractTileEssentiaSmelter) tile).getCurrentEssentiaTotal();
            AuraHelper.polluteAura(worldIn, pos, (float) essentia, true);
            IItemHandler handler = ((AbstractTileEssentiaSmelter) tile).handler;
            for(int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.extractItem(i, handler.getSlotLimit(i), false);
                Block.spawnAsEntity(worldIn, pos, stack);
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorInputOverride(@NotNull IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorInputOverride(@NotNull IBlockState blockState, @NotNull World worldIn, @NotNull BlockPos pos) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof AbstractTileEssentiaSmelter) {
            int capacity = ((AbstractTileEssentiaSmelter) tile).getMaxEssentiaCapacity();
            int stored = ((AbstractTileEssentiaSmelter) tile).getCurrentEssentiaTotal();
            return (int) (((double) stored / (double) capacity) * 16.0);
        }
        return 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta & -5)).withProperty(ENABLED, (meta & 4) != 0);
    }

    @Override
    public int getMetaFromState(@NotNull IBlockState state) {
        return (state.getValue(ENABLED) ? 4 : 0) | state.getValue(FACING).getHorizontalIndex();
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, ENABLED);
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Override
    public @Nullable TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        return new TileSmelterPA();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@NotNull IBlockState stateIn, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Random rand) {
        if (stateIn.getValue(ENABLED)) {
            EnumFacing front = stateIn.getValue(FACING);
            float x = (float) pos.getX() + 0.5f;
            float y = (float) pos.getY() + 0.2f + rand.nextFloat() * 5.0f / 16.0f;
            float z = (float) pos.getZ() + 0.5f;
            float smokeOffset = 0.52f;
            float flameOffset = rand.nextFloat() * 0.5f - 0.25f;

            switch (front) {
                case NORTH:
                    x += flameOffset;
                    z -= smokeOffset;
                    break;
                case SOUTH:
                    x += smokeOffset;
                    z += flameOffset;
                    break;
                case WEST:
                    x -= smokeOffset;
                    z += flameOffset;
                    break;
                case EAST:
                    x += flameOffset;
                    z += smokeOffset;
                    break;
            }

            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0, 0, 0);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0, 0, 0);
        }
    }
}
