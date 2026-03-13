package mod.emt.planarartifice.block;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.block.base.BlockContainerPA;
import mod.emt.planarartifice.tile.TileStarvingChest;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.ThaumcraftInvHelper;

public class BlockStarvingChest extends BlockContainerPA {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final AxisAlignedBB BLOCK_AABB = new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375);
    public ResourceLocation textureLocation;
    public int suctionRange;

    public BlockStarvingChest(String name, int suctionRange, ResourceLocation textureLocation) {
        super(name, Material.WOOD, MapColor.WOOD);
        this.setHardness(2.5f);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.suctionRange = suctionRange;
        this.textureLocation = textureLocation;
    }

    public BlockStarvingChest(String name, int suctionRange) {
        this(name, suctionRange, new ResourceLocation(PlanarArtifice.MOD_ID, "textures/models/" + name + ".png"));
    }

    public int getSuctionRange(World world, BlockPos pos, IBlockState state) {
        return this.suctionRange;
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return BLOCK_AABB;
    }

    @Override
    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(!worldIn.isRemote && tile instanceof IInventory) {
            playerIn.displayGUIChest((IInventory) tile);
        }
        return true;
    }

    @Override
    public void onEntityCollision(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Entity entity) {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileStarvingChest && entity instanceof EntityItem) {
            EntityItem entityItem = (EntityItem) entity;
            TileStarvingChest chest = (TileStarvingChest) tile;
            if(chest.validateEntity(entityItem)) {
                if(!world.isRemote) {
                    ItemStack leftovers = ThaumcraftInvHelper.insertStackAt(world, pos, EnumFacing.UP, entityItem.getItem(), false);
                    if(!leftovers.isEmpty() || leftovers.getCount() != entityItem.getItem().getCount()) {
                        entity.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.25F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
                    }

                    if(!leftovers.isEmpty()) {
                        entityItem.setDead();
                    } else {
                        entityItem.setItem(leftovers);
                    }
                    chest.markDirty();
                }
            }
        }
        super.onEntityCollision(world, pos, state, entity);
    }

    @Override
    public void breakBlock(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof IInventory) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tile);
            worldIn.updateComparatorOutputLevel(pos, this);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean canHarvestBlock(@NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EntityPlayer player) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorInputOverride(@NotNull IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorInputOverride(@NotNull IBlockState blockState, World worldIn, @NotNull BlockPos pos) {
        TileEntity tile = worldIn.getTileEntity(pos);
        return tile instanceof IInventory ? Container.calcRedstoneFromInventory((IInventory) tile) : 0;
    }

    @Override
    public void onBlockPlacedBy(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileStarvingChest) {
                ((TileStarvingChest)tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public @NotNull IBlockState getStateForPlacement(@NotNull World world, @NotNull BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @NotNull EntityLivingBase placer, @NotNull EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull IBlockState withRotation(@NotNull IBlockState state, @NotNull Rotation rot) {
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
    public @NotNull BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        //TODO: Check this.
        return EnumBlockRenderType.INVISIBLE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasCustomBreakingProgress(@NotNull IBlockState state) {
        return true;
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Override
    public @Nullable TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        return new TileStarvingChest();
    }
}
