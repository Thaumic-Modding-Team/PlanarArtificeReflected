package mod.emt.planarartifice.block;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.registry.ModGuiHandlerPA;
import mod.emt.planarartifice.tile.TileAlkimiumSmelterPA;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.blocks.IBlockEnabled;
import thaumcraft.common.blocks.IBlockFacingHorizontal;
import thaumcraft.common.lib.utils.BlockStateUtils;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.essentia.TileSmelter;

import java.util.Random;

public class BlockAlkimiumSmelteryPA extends BlockMaterialPA implements IBlockEnabled, IBlockFacingHorizontal {
    protected static boolean keepInventory = false;
    protected static boolean spillEssentia = true;

    private final int speed;
    private final float efficiency;
    private final int capacity;

    public BlockAlkimiumSmelteryPA(int speed, float efficiency, int capacity) {
        super(Material.IRON, MapColor.GREEN, 4.0F, 6.0F, SoundType.METAL, false);
        this.speed = speed;
        this.efficiency = efficiency;
        this.capacity = capacity;
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileAlkimiumSmelterPA)) {
            return false;
        }
        player.openGui(PlanarArtifice.instance, ModGuiHandlerPA.ID_ALKIMIUM_SMELTERY, world, pos.getX(), pos.getY(),
                pos.getZ());
        return true;
    }

    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileAlkimiumSmelterPA();
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState bs = this.getDefaultState();
        bs = bs.withProperty(IBlockFacingHorizontal.FACING, placer.getHorizontalFacing().getOpposite());
        bs = bs.withProperty(IBlockEnabled.ENABLED, false);
        return bs;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos pos2) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileSmelter) {
            ((TileSmelter) te).checkNeighbours();
        }
    }

    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return BlockStateUtils.isEnabled(world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos))) ? 13 : 0;
    }

    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IInventory) {
            return Container.calcRedstoneFromInventory((IInventory) te);
        }
        return 0;
    }

    public static void setFurnaceState(World world, BlockPos pos, boolean state) {
        if (state == BlockStateUtils.isEnabled(world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos))))
            return;

        TileEntity tileentity = world.getTileEntity(pos);
        keepInventory = true;
        world.setBlockState(pos, world.getBlockState(pos).withProperty(IBlockEnabled.ENABLED, state), 3);
        world.setBlockState(pos, world.getBlockState(pos).withProperty(IBlockEnabled.ENABLED, state), 3);
        if (tileentity != null) {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
        keepInventory = false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileSmelter && !worldIn.isRemote && ((TileSmelter) tileentity).vis > 0) {
            int ess = ((TileSmelter) tileentity).vis;
            AuraHelper.polluteAura(worldIn, pos, ess, true);
        }
        InventoryUtils.dropItems(worldIn, pos);
        super.breakBlock(worldIn, pos, state);
    }

    @SideOnly(value = Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World w, BlockPos pos, Random r) {
        if (BlockStateUtils.isEnabled(state)) {
            float f = (float) pos.getX() + 0.5f;
            float f1 = (float) pos.getY() + 0.2f + r.nextFloat() * 5.0f / 16.0f;
            float f2 = (float) pos.getZ() + 0.5f;
            float f3 = 0.52f;
            float f4 = r.nextFloat() * 0.5f - 0.25f;
            if (BlockStateUtils.getFacing(state) == EnumFacing.WEST) {
                w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (f - f3), f1, (f2 + f4), 0.0,
                        0.0, 0.0);
                w.spawnParticle(EnumParticleTypes.FLAME, (f - f3), f1, (f2 + f4), 0.0, 0.0,
                        0.0);
            }
            if (BlockStateUtils.getFacing(state) == EnumFacing.EAST) {
                w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (f + f3), f1, (f2 + f4), 0.0,
                        0.0, 0.0);
                w.spawnParticle(EnumParticleTypes.FLAME, (f + f3), f1, (f2 + f4), 0.0, 0.0,
                        0.0);
            }
            if (BlockStateUtils.getFacing(state) == EnumFacing.NORTH) {
                w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (f + f4), f1, (f2 - f3), 0.0,
                        0.0, 0.0);
                w.spawnParticle(EnumParticleTypes.FLAME, (f + f4), f1, (f2 - f3), 0.0, 0.0,
                        0.0);
            }
            if (BlockStateUtils.getFacing(state) == EnumFacing.SOUTH) {
                w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f + f4, f1, (f2 + f3), 0.0,
                        0.0, 0.0);
                w.spawnParticle(EnumParticleTypes.FLAME, (f + f4), f1, (f2 + f3), 0.0, 0.0,
                        0.0);
            }
        }
    }

    protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
        boolean flag = !worldIn.isBlockPowered(pos);
        if (flag != state.getValue(IBlockEnabled.ENABLED)) {
            worldIn.setBlockState(pos, state.withProperty(IBlockEnabled.ENABLED, flag), 3);
        }

    }

    public void updateFacing(World world, BlockPos pos, EnumFacing face) {
        if (face != BlockStateUtils.getFacing(world.getBlockState(pos)) && face.getHorizontalIndex() != 0)
            world.setBlockState(pos, world.getBlockState(pos).withProperty(IBlockFacingHorizontal.FACING, face), 3);

    }

    public IBlockState getStateFromMeta(int meta) {
        IBlockState bs = this.getDefaultState();
        bs = bs.withProperty(IBlockFacingHorizontal.FACING, BlockStateUtils.getFacing(meta));
        bs = bs.withProperty(IBlockEnabled.ENABLED, BlockStateUtils.isEnabled(meta));

        return bs;
    }

    public int getMetaFromState(IBlockState state) {
        int i = state.getValue(IBlockFacingHorizontal.FACING).getIndex();

        if (!(Boolean) state.getValue(IBlockEnabled.ENABLED)) {
            i |= 8;
        }
        return i;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, IBlockFacingHorizontal.FACING, IBlockEnabled.ENABLED);
    }

    public int getSpeed() {
        return this.speed;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public int getCapacity() {
        return this.capacity;
    }
}
