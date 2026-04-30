package mod.emt.planarartifice.item.blocks;

import mod.emt.planarartifice.tile.TileMirroredJar;
import mod.emt.planarartifice.utils.helper.WorldHelper;
import mod.emt.planarartifice.utils.world.MirroredJarData;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.blocks.essentia.BlockJarItem;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ItemBlockMirroredJar extends BlockJarItem {
    public ItemBlockMirroredJar(Block block) {
        super(block);
        this.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        this.setTranslationKey(block.getTranslationKey());
        this.setCreativeTab(block.getCreativeTab());
    }

    @Override
    public @NotNull EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileMirroredJar && player.isSneaking()) {
            TileMirroredJar tileJar = (TileMirroredJar) tile;
            if(!world.isRemote) {
                ItemStack heldStack = player.getHeldItem(hand);
                if(!this.isAspectListEmpty(heldStack)) {
                    player.sendStatusMessage(new TextComponentTranslation("chat.planarartifice:mirrored_jar.failed").setStyle(new Style().setColor(TextFormatting.DARK_PURPLE).setItalic(true)), true);
                    return EnumActionResult.SUCCESS;
                }

                UUID tileUUID = tileJar.getLinkUUID();
                if(tileUUID == null) {
                    tileUUID = UUID.randomUUID();
                    tileJar.setLinkUUID(tileUUID);
                }
                ItemStack jarStack = heldStack.copy();
                jarStack.setCount(1);
                if(!player.isCreative()) {
                    heldStack.shrink(1);
                }
                this.setLinkUUID(jarStack, tileUUID);
                ItemHandlerHelper.giveItemToPlayer(player, jarStack, player.inventory.currentItem);
                player.sendStatusMessage(new TextComponentTranslation("chat.planarartifice:mirrored_jar.success").setStyle(new Style().setColor(TextFormatting.DARK_PURPLE).setItalic(true)), true);
            }
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        boolean flag = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
        UUID linkUUID = this.getLinkUUID(stack);
        if(linkUUID != null) {
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileMirroredJar) {
                ((TileMirroredJar) tile).setLinkUUID(linkUUID);
                ((TileMirroredJar) tile).syncJarData();
            }
        }
        return flag;
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.RARE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        UUID linkUUID = this.getLinkUUID(stack);
        if(linkUUID != null) {
            tooltip.add(TextFormatting.BLUE + I18n.format("tooltip.planarartifice:mirrored_jar.linked"));
            if(flagIn.isAdvanced()) {
                tooltip.add(I18n.format("tooltip.planarartifice:mirrored_jar.info") + " " + TextFormatting.DARK_GRAY + linkUUID);
            }
        } else {
            tooltip.add(TextFormatting.DARK_GRAY + I18n.format("tooltip.planarartifice:mirrored_jar.unlinked"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public boolean isAspectListEmpty(ItemStack stack) {
        AspectList aspectList = this.getAspects(stack);
        return aspectList == null || aspectList.size() == 0;
    }

    @Override
    public AspectList getAspects(ItemStack itemstack) {
        UUID linkUUID = this.getLinkUUID(itemstack);
        if(linkUUID != null) {
            MirroredJarData data = WorldHelper.getMirroredJarData(linkUUID);
            if(data != null && data.getAspect() != null && data.getAmount() > 0) {
                return new AspectList().add(data.getAspect(), data.getAmount());
            }
        }
        return super.getAspects(itemstack);
    }

    @Nullable
    public UUID getLinkUUID(ItemStack stack) {
        if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("linkUUID")) {
            return UUID.fromString(stack.getTagCompound().getString("linkUUID"));
        }
        return null;
    }

    public void setLinkUUID(ItemStack stack, UUID linkUUID) {
        stack.setTagInfo("linkUUID", new NBTTagString(linkUUID.toString()));
    }
}
