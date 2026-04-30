package mod.emt.planarartifice.block.essentia;

import mod.emt.planarartifice.block.base.BlockContainerPA;
import mod.emt.planarartifice.item.blocks.ItemBlockMirroredJar;
import mod.emt.planarartifice.tile.TileMirroredJar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.blocks.ILabelable;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.essentia.BlockJarItem;
import thaumcraft.common.lib.SoundsTC;

import java.util.UUID;

public class BlockMirroredJar extends BlockContainerPA implements ILabelable {
    public static AxisAlignedBB BLOCK_AABB = new AxisAlignedBB(0.1875, 0, 0.1875, 0.8125, 0.75, 0.8125);

    public BlockMirroredJar() {
        super("mirrored_jar", Material.GLASS);
        this.setHardness(0.3f);
        this.setSoundType(SoundsTC.JAR);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return BLOCK_AABB;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull SoundType getSoundType() {
        return SoundsTC.JAR;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public @NotNull BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Override
    public @Nullable TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        return new TileMirroredJar();
    }

    @Override
    public void dropBlockAsItemWithChance(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, float chance, int fortune) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileMirroredJar) {
            this.spawnFilledJar(worldIn, pos, state, (TileMirroredJar) tile);
        } else {
            super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
        }
    }

    @Override
    public void harvestBlock(@NotNull World worldIn, @NotNull EntityPlayer player, @NotNull BlockPos pos, @NotNull IBlockState state, @Nullable TileEntity tile, @NotNull ItemStack stack) {
        if(tile instanceof TileMirroredJar) {
            this.spawnFilledJar(worldIn, pos, state, (TileMirroredJar) tile);
        } else {
            super.harvestBlock(worldIn, player, pos, state, tile, stack);
        }
    }

    private void spawnFilledJar(World world, BlockPos pos, IBlockState state, TileMirroredJar tile) {
        ItemStack drop = new ItemStack(this, 1, this.getMetaFromState(state));
        if (tile.amount > 0) {
            ((BlockJarItem)drop.getItem()).setAspects(drop, (new AspectList()).add(tile.aspect, tile.amount));
        }

        if (tile.aspectFilter != null) {
            drop.setTagInfo("AspectFilter", new NBTTagString(tile.aspectFilter.getTag()));
        }
        if(tile.isLinked()) {
            drop.setTagInfo("linkUUID", new NBTTagString(tile.getLinkUUID().toString()));
        }

        if (tile.blocked) {
            spawnAsEntity(world, pos, new ItemStack(ItemsTC.jarBrace));
        }

        spawnAsEntity(world, pos, drop);
    }

    @Override
    public void onBlockPlacedBy(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityLivingBase placer, @NotNull ItemStack stack) {
        int l = MathHelper.floor((placer.rotationYaw * 4.0 / 360.0) + 0.5) & 3;
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileMirroredJar) {
            switch (placer.getHorizontalFacing()) {
                case NORTH:
                    ((TileMirroredJar)tile).facing = 3;
                    break;
                case SOUTH:
                    ((TileMirroredJar)tile).facing = 2;
                    break;
                case WEST:
                    ((TileMirroredJar)tile).facing = 5;
                    break;
                case EAST:
                    ((TileMirroredJar)tile).facing = 4;
                    break;
            }
            if(stack.getItem() instanceof ItemBlockMirroredJar) {
                UUID linkUUID = ((ItemBlockMirroredJar) stack.getItem()).getLinkUUID(stack);
                if(linkUUID != null) {
                    ((TileMirroredJar) tile).setLinkUUID(linkUUID);
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileMirroredJar) {
            TileMirroredJar jarTile = (TileMirroredJar) tile;
            ItemStack heldStack = playerIn.getHeldItem(hand);
            if(heldStack.getItem() == ItemsTC.jarBrace) {
                return this.handleJarBrace(worldIn, pos, heldStack, jarTile);
            } else if(playerIn.isSneaking() && jarTile.aspectFilter != null && facing.ordinal() == jarTile.facing) {
                return this.handleLabelRemoval(worldIn, pos, facing, jarTile);
            } else if(playerIn.isSneaking() && heldStack.isEmpty()) {
                return this.handleJarEmptying(worldIn, pos, jarTile);
            } else if(heldStack.getItem() == ItemsTC.phial) {
                if(heldStack.getItemDamage() == 0) {
                    return this.fillPhialFromJar(playerIn, heldStack, jarTile);
                } else {
                    return this.emptyPhialIntoJar(playerIn, heldStack, jarTile);
                }
            }
        }
        return false;
    }

    protected boolean handleJarBrace(World world, BlockPos pos, ItemStack stack, TileMirroredJar tile) {
        if(!tile.blocked) {
            tile.blocked = true;
            stack.shrink(1);
            if (world.isRemote) {
                world.playSound((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, SoundsTC.key, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            } else {
                tile.markDirty();
            }
            return true;
        }
        return false;
    }

    protected boolean handleLabelRemoval(World world, BlockPos pos, EnumFacing facing, TileMirroredJar tile) {
        tile.aspectFilter = null;
        if (world.isRemote) {
            world.playSound(pos.getX() + 0.5,  pos.getY() + 0.5, pos.getZ() + 0.5, SoundsTC.page, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        } else {
            world.spawnEntity(new EntityItem(world, pos.getX() + 0.5 + facing.getXOffset() / 3.0, pos.getY() + 0.5F, pos.getZ() + 0.5F + facing.getZOffset() / 3.0F, new ItemStack(ItemsTC.label)));
        }
        return true;
    }

    protected boolean handleJarEmptying(World world, BlockPos pos, TileMirroredJar tile) {
        if (tile.aspectFilter == null) {
            tile.aspect = null;
        }

        if (world.isRemote) {
            world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundsTC.jar, SoundCategory.BLOCKS, 0.4F, 1.0F, false);
            world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 0.5F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F, false);
        } else {
            AuraHelper.polluteAura(world, pos, (float) tile.amount, true);
        }

        tile.amount = 0;
        tile.updateJarData();
        tile.markDirty();
        return true;
    }

    protected boolean fillPhialFromJar(EntityPlayer player, ItemStack stack, TileMirroredJar tile) {
        int base = 10;
        if (tile.amount >= base) {
            Aspect aspect = Aspect.getAspect(tile.aspect.getTag());
            if (tile.takeFromContainer(aspect, base)) {
                if(!player.isCreative()) {
                    stack.shrink(1);
                    ItemStack phial = new ItemStack(ItemsTC.phial, 1, 1);
                    ((IEssentiaContainerItem) phial.getItem()).setAspects(phial, (new AspectList()).add(aspect, base));
                    ItemHandlerHelper.giveItemToPlayer(player, phial, player.inventory.currentItem);
                }
                player.playSound(SoundEvents.ITEM_BOTTLE_FILL, 0.25F, 1.0F);
                return true;
            }
        }
        return false;
    }

    protected boolean emptyPhialIntoJar(EntityPlayer player, ItemStack stack, TileMirroredJar tile) {
        int base = 10;
        AspectList al = ((IEssentiaContainerItem) stack.getItem()).getAspects(stack);
        if (al != null && al.size() == 1) {
            Aspect aspect = al.getAspects()[0];
            if (tile.amount <= 250 - base && tile.doesContainerAccept(aspect)) {
                if (tile.addToContainer(aspect, base) == 0) {
                    if(!player.isCreative()) {
                        stack.shrink(1);
                        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemsTC.phial), player.inventory.currentItem);
                    }
                    player.playSound(SoundEvents.ITEM_BOTTLE_FILL, 0.25F, 1.0F);
                    return true;
                }
            }
        }
        return false;
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
        if(tile instanceof TileMirroredJar) {
            float fill = ((TileMirroredJar) tile).amount / 250.0f;
            int power = MathHelper.clamp((int) (15.0f * fill), 0, 15);
            if(fill > 0 && power == 0) {
                power = 1;
            }
            return power;
        }
        return super.getComparatorInputOverride(blockState, worldIn, pos);
    }

    @Override
    public boolean applyLabel(EntityPlayer player, BlockPos pos, EnumFacing enumFacing, ItemStack labelStack) {
        TileEntity tile = player.world.getTileEntity(pos);
        if (tile instanceof TileMirroredJar) {
            TileMirroredJar tileEntity = (TileMirroredJar) tile;
            if (labelStack.getItem() instanceof IEssentiaContainerItem) {
                IEssentiaContainerItem labelItem = (IEssentiaContainerItem)labelStack.getItem();
                if (tileEntity.aspectFilter == null) {
                    if (tileEntity.amount == 0 && labelItem.getAspects(labelStack) == null) {
                        return false;
                    }

                    if (tileEntity.amount == 0 && labelItem.getAspects(labelStack) != null) {
                        tileEntity.aspect = labelItem.getAspects(labelStack).getAspects()[0];
                    }

                    this.onBlockPlacedBy(player.world, pos, player.world.getBlockState(pos), player, ItemStack.EMPTY);
                    tileEntity.aspectFilter = tileEntity.aspect;
                    player.world.markAndNotifyBlock(pos, player.world.getChunk(pos), player.world.getBlockState(pos), player.world.getBlockState(pos), 3);
                    tileEntity.markDirty();
                    player.world.playSound(null, pos, SoundsTC.jar, SoundCategory.BLOCKS, 0.4F, 1.0F);
                    return true;
                }
            }
        }

        return false;
    }
}
