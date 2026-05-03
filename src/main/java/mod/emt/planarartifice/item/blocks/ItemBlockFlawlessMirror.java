package mod.emt.planarartifice.item.blocks;

import mod.emt.planarartifice.registry.ModBlocksPA;
import mod.emt.planarartifice.tile.TileFlawlessMirror;
import mod.emt.planarartifice.tile.TileFlawlessMirrorEssentia;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.common.lib.SoundsTC;

import java.util.List;

public class ItemBlockFlawlessMirror extends ItemBlockPA {
    public ItemBlockFlawlessMirror(Block block) {
        super(block, EnumRarity.RARE);
        this.addPropertyOverride(new ResourceLocation("linked"), ((stack, worldIn, entityIn) -> hasLinkTagInfo(stack) ? 1 : 0));
    }

    @Override
    public @NotNull EnumActionResult onItemUseFirst(@NotNull EntityPlayer player, @NotNull World world, @NotNull BlockPos pos, @NotNull EnumFacing side, float hitX, float hitY, float hitZ, @NotNull EnumHand hand) {
        TileEntity tile = world.getTileEntity(pos);
        boolean isMirror = false;
        boolean validLink = false;
        if(tile instanceof TileFlawlessMirror && this.block == ModBlocksPA.FLAWLESS_MIRROR) {
            isMirror = true;
            validLink = ((TileFlawlessMirror) tile).isLinkValid();
        } else if(tile instanceof TileFlawlessMirrorEssentia && this.block == ModBlocksPA.FLAWLESS_MIRROR_ESSENTIA) {
            isMirror = true;
            validLink = ((TileFlawlessMirrorEssentia) tile).isLinkValid();
        }

        if (isMirror) {
            ItemStack heldStack = player.getHeldItem(hand);
            if (validLink) {
                if(!world.isRemote) {
                    player.sendMessage(new TextComponentTranslation("chat.planarartifice:mirror.already_linked")
                            .setStyle(new Style().setColor(TextFormatting.DARK_PURPLE).setItalic(true)));
                }
            } else {
                if (!world.isRemote) {
                    if (player.isCreative()) {
                        heldStack = heldStack.copy();
                        heldStack.setCount(1);
                    } else {
                        heldStack = heldStack.splitStack(1);
                    }
                    heldStack.setCount(1);
                    heldStack.setTagInfo("linkX", new NBTTagInt(pos.getX()));
                    heldStack.setTagInfo("linkY", new NBTTagInt(pos.getY()));
                    heldStack.setTagInfo("linkZ", new NBTTagInt(pos.getZ()));
                    heldStack.setTagInfo("linkDim", new NBTTagInt(world.provider.getDimension()));

                    if (!player.inventory.addItemStackToInventory(heldStack)) {
                        world.spawnEntity(new EntityItem(world, player.posX, player.posY, player.posZ, heldStack));
                    }

                    if (!player.capabilities.isCreativeMode) {
                        player.getHeldItem(hand).shrink(1);
                    }

                    player.inventoryContainer.detectAndSendChanges();
                } else {
                    world.playSound(player, pos, SoundsTC.jar, SoundCategory.BLOCKS, 1.0F, 2.0F);
                }
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    @Override
    public boolean placeBlockAt(@NotNull ItemStack stack, @NotNull EntityPlayer player, @NotNull World world, @NotNull BlockPos pos, @NotNull EnumFacing side, float hitX, float hitY, float hitZ, @NotNull IBlockState newState) {
        boolean flag = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
        if(flag && !world.isRemote && stack.getTagCompound() != null && this.hasLinkTagInfo(stack)) {
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileFlawlessMirror) {
                ((TileFlawlessMirror) tile).linkX = stack.getTagCompound().getInteger("linkX");
                ((TileFlawlessMirror) tile).linkY = stack.getTagCompound().getInteger("linkY");
                ((TileFlawlessMirror) tile).linkZ = stack.getTagCompound().getInteger("linkZ");
                ((TileFlawlessMirror) tile).linkDim = stack.getTagCompound().getInteger("linkDim");
                ((TileFlawlessMirror) tile).restoreLink();
            } else if(tile instanceof TileFlawlessMirrorEssentia) {
                ((TileFlawlessMirrorEssentia) tile).linkX = stack.getTagCompound().getInteger("linkX");
                ((TileFlawlessMirrorEssentia) tile).linkY = stack.getTagCompound().getInteger("linkY");
                ((TileFlawlessMirrorEssentia) tile).linkZ = stack.getTagCompound().getInteger("linkZ");
                ((TileFlawlessMirrorEssentia) tile).linkDim = stack.getTagCompound().getInteger("linkDim");
                ((TileFlawlessMirrorEssentia) tile).restoreLink();
            }
        }
        return flag;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        if(stack.getTagCompound() != null && this.hasLinkTagInfo(stack)) {
            int lx = stack.getTagCompound().getInteger("linkX");
            int ly = stack.getTagCompound().getInteger("linkY");
            int lz = stack.getTagCompound().getInteger("linkZ");
            int ldim = stack.getTagCompound().getInteger("linkDim");
            String desc = "" + ldim;
            World world = DimensionManager.getWorld(ldim);
            if (world != null) {
                desc = world.provider.getDimensionType().getName();
            }
            tooltip.add("Linked to " + lx + "," + ly + "," + lz + " in " + desc);
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    protected boolean hasLinkTagInfo(ItemStack stack) {
        if(stack.getTagCompound() != null) {
            NBTTagCompound tag = stack.getTagCompound();
            return tag.hasKey("linkX") && tag.hasKey("linkY") && tag.hasKey("linkZ") && tag.hasKey("linkDim");
        }
        return false;
    }
}
