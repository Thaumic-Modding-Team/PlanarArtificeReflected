package mod.emt.planarartifice.block;

import mod.emt.planarartifice.block.base.BlockContainerPA;
import mod.emt.planarartifice.tile.TileFlawlessMirror;
import mod.emt.planarartifice.tile.TileFlawlessMirrorEssentia;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.common.lib.SoundsTC;

public class BlockFlawlessMirror extends BlockContainerPA {
    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);
    public static final AxisAlignedBB[] MIRROR_AABBS = new AxisAlignedBB[] {
            new AxisAlignedBB(0, 0.875, 0, 1.0, 1.0, 1.0),  //Down
            new AxisAlignedBB(0, 0, 0, 1.0, 0.125, 1.0),    //Up

            new AxisAlignedBB(0, 0, 0.875, 1.0, 1.0, 1.0),
            new AxisAlignedBB(0, 0, 0, 1.0F, 1.0, 0.125),

            new AxisAlignedBB(0.875, 0, 0, 1.0, 1.0F, 1.0),
            new AxisAlignedBB(0, 0, 0, 0.125, 1.0, 1.0),
    };
    private final Class<? extends TileEntity> tileClass;

    public BlockFlawlessMirror(Class<? extends TileEntity> tileClass, String name) {
        super(name, Material.IRON);
        this.setResistance(8.0f);
        this.setHardness(0.1f);
        this.setSoundType(SoundsTC.JAR);
        this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP);
        this.tileClass = tileClass;
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return tileClass != null;
    }

    @Override
    public @Nullable TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        try {
            return this.tileClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull AxisAlignedBB getBoundingBox(IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        return MIRROR_AABBS[facing.ordinal()];
    }

    @Override
    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, EntityPlayer player, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        return true;
    }

    @Override
    public @NotNull SoundType getSoundType(@NotNull IBlockState state, @NotNull World world, @NotNull BlockPos pos, @Nullable Entity entity) {
        return SoundsTC.JAR;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos) {
        EnumFacing facing = state.getValue(FACING);
        BlockPos checkPos = pos.offset(facing.getOpposite());
        if(!world.getBlockState(checkPos).isSideSolid(world, checkPos, facing)) {
            this.dropBlockAsItem(world, pos, this.getDefaultState(), 0);
            world.setBlockToAir(pos);
        }
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        BlockPos checkPos = pos.offset(side.getOpposite());
        return worldIn.getBlockState(checkPos).isSideSolid(worldIn, checkPos, side);
    }

    @Override
    public boolean canHarvestBlock(@NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EntityPlayer player) {
        return true;
    }

    @Override
    public int damageDropped(@NotNull IBlockState state) {
        return 0;
    }

    @Override
    public void dropBlockAsItemWithChance(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, float chance, int fortune) {
        this.dropMirror(worldIn, pos, state, worldIn.getTileEntity(pos));
    }

    @Override
    public void harvestBlock(@NotNull World worldIn, @NotNull EntityPlayer player, @NotNull BlockPos pos, @NotNull IBlockState state, @Nullable TileEntity te, @NotNull ItemStack stack) {
        this.dropMirror(worldIn, pos, state, te);
    }

    public void dropMirror(World world, BlockPos pos, IBlockState state, TileEntity tile) {
        if(tile instanceof TileFlawlessMirror) {
            TileFlawlessMirror mirror = (TileFlawlessMirror) tile;
            ItemStack drop = new ItemStack(this);
            if(mirror.linked) {
                drop.setTagInfo("linkX", new NBTTagInt(mirror.linkX));
                drop.setTagInfo("linkY", new NBTTagInt(mirror.linkY));
                drop.setTagInfo("linkZ", new NBTTagInt(mirror.linkZ));
                drop.setTagInfo("linkDim", new NBTTagInt(mirror.linkDim));
                mirror.invalidateLink();
            }
            spawnAsEntity(world, pos, drop);
        } else if(tile instanceof TileFlawlessMirrorEssentia) {
            TileFlawlessMirrorEssentia mirror = (TileFlawlessMirrorEssentia) tile;
            ItemStack drop = new ItemStack(this);
            if(mirror.linked) {
                drop.setTagInfo("linkX", new NBTTagInt(mirror.linkX));
                drop.setTagInfo("linkY", new NBTTagInt(mirror.linkY));
                drop.setTagInfo("linkZ", new NBTTagInt(mirror.linkZ));
                drop.setTagInfo("linkDim", new NBTTagInt(mirror.linkDim));
                mirror.invalidateLink();
            }
            spawnAsEntity(world, pos, drop);
        }
    }

    @Override
    public void onEntityCollision(World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Entity entity) {
        if(!world.isRemote && this.tileClass.equals(TileFlawlessMirror.class) && entity instanceof EntityItem && entity.isEntityAlive() && entity.timeUntilPortal == 0) {
            TileEntity tile = world.getTileEntity(pos);
            if(tile != null && tile.getClass().equals(TileFlawlessMirror.class)) {
                ((TileFlawlessMirror) tile).transport((EntityItem) entity);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @NotNull EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).ordinal();
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState withMirror(@NotNull IBlockState state, @NotNull Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

}
