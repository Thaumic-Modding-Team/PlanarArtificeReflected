package mod.emt.planarartifice.item.bauble;

import baubles.api.BaubleType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.items.IVisDiscountGear;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.tiles.devices.TileMirror;

import java.util.List;

public class ItemMirroredAmulet extends AbstractBaublePA implements IVisDiscountGear {
    public ItemMirroredAmulet() {
        super("mirrored_amulet", EnumRarity.UNCOMMON);
    }

    @Override
    public @NotNull EnumActionResult onItemUseFirst(@NotNull EntityPlayer player, @NotNull World world, @NotNull BlockPos pos, @NotNull EnumFacing side, float hitX, float hitY, float hitZ, @NotNull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileMirror) {
            this.linkMirror(player, stack, (TileMirror) tile);
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.AMULET;
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

    @Override
    public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer) {
        return 3;
    }

    public void linkMirror(EntityPlayer player, ItemStack stack, TileMirror mirror) {
        World world = player.world;
        if(!world.isRemote) {
            stack.setTagInfo("linkX", new NBTTagInt(mirror.getPos().getX()));
            stack.setTagInfo("linkY", new NBTTagInt(mirror.getPos().getY()));
            stack.setTagInfo("linkZ", new NBTTagInt(mirror.getPos().getZ()));
            stack.setTagInfo("linkDim", new NBTTagInt(mirror.getWorld().provider.getDimension()));
        } else {
            world.playSound(player, mirror.getPos(), SoundsTC.jar, SoundCategory.BLOCKS, 1.0F, 2.0F);
        }
    }

    public boolean hasLinkTagInfo(ItemStack stack) {
        if(stack.getTagCompound() != null) {
            NBTTagCompound tag = stack.getTagCompound();
            return tag.hasKey("linkX") && tag.hasKey("linkY") && tag.hasKey("linkZ") && tag.hasKey("linkDim");
        }
        return false;
    }
}
