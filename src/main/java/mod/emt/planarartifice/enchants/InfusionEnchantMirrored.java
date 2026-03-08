package mod.emt.planarartifice.enchants;

import com.invadermonky.thaumicapi.api.ThaumicAPI;
import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.utils.helper.StringHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
import thaumcraft.common.tiles.devices.TileMirror;

import java.util.List;

@Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID)
public class InfusionEnchantMirrored {
    //TODO: Change required research.
    public static EnumInfusionEnchantment MIRRORED = ThaumicAPI.registerInfusionEnchantment(
            "MIRRORED", 1, "INFUSIONENCHANTMENT", "weapon", "pickaxe", "shovel", "hoe", "axe");

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<String> tooltips = event.getToolTip();
        if(hasMirroredInfusion(stack)) {
            boolean found = false;
            int i = 0;
            for (; i < tooltips.size(); i++) {
                String tooltip = tooltips.get(i);
                if (tooltip.equals("§6enchantment.infusion.MIRRORED")) {
                    found = true;
                    break;
                }
            }

            if (found) {
                if(isLinked(stack)) {
                    tooltips.set(i, I18n.format("enchantment.infusion.MIRRORED.linked"));
                    if(GuiScreen.isShiftKeyDown()) {
                        String dimName = getLinkedDimensionName(stack);
                        BlockPos pos = getLinkedPosition(stack);
                        tooltips.add(i + 1, "  " + I18n.format("enchantment.infusion.MIRRORED.linked.dim", dimName));
                        tooltips.add(i + 2, "  " + I18n.format("enchantment.infusion.MIRRORED.linked.pos", pos.getX(), pos.getY(), pos.getZ()));
                    }
                } else {
                    tooltips.set(i, I18n.format("enchantment.infusion.MIRRORED.unlinked"));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        if(!world.isRemote && event.getEntityPlayer().isSneaking()) {
            ItemStack heldStack = event.getItemStack();
            TileEntity tile = world.getTileEntity(event.getPos());
            if(hasMirroredInfusion(heldStack) && tile instanceof TileMirror) {
                if(!isLinked(heldStack)) {
                    setLink(heldStack, tile.getWorld().provider.getDimension(), tile.getPos());
                } else {
                    int dimId = getLinkedDimension(heldStack);
                    BlockPos pos = getLinkedPosition(heldStack);
                    if (dimId != world.provider.getDimension() || !pos.equals(tile.getPos())) {
                        setLink(heldStack, tile.getWorld().provider.getDimension(), tile.getPos());
                    } else {
                        removeLink(heldStack);
                    }
                }
                event.setUseItem(Event.Result.DENY);
                event.setUseBlock(Event.Result.DENY);
                event.setCancellationResult(EnumActionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer player = event.getHarvester();
        if(player != null) {
            ItemStack heldStack = player.getHeldItemMainhand();
            if(hasMirroredInfusion(heldStack) && isLinked(heldStack)) {
                NonNullList<ItemStack> drops = NonNullList.create();
                drops.addAll(event.getDrops());
                if(attemptTransfer(heldStack, drops)) {
                    event.getDrops().clear();
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingDrops(LivingDropsEvent event) {
        if(event.isRecentlyHit() && event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            ItemStack heldStack = player.getHeldItemMainhand();
            if(hasMirroredInfusion(heldStack) && isLinked(heldStack)) {
                NonNullList<ItemStack> drops = NonNullList.create();
                for(EntityItem entityItem : event.getDrops()) {
                    ItemStack drop = entityItem.getItem();
                    drops.add(drop);
                }
                if(attemptTransfer(heldStack, drops)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    public static boolean hasMirroredInfusion(ItemStack stack) {
        return EnumInfusionEnchantment.getInfusionEnchantmentLevel(stack, MIRRORED) > 0;
    }

    public static boolean isLinked(ItemStack stack) {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("mirrorLink");
    }

    public static void setLink(ItemStack stack, int dimension, BlockPos pos) {
        NBTTagCompound linkTag = new NBTTagCompound();
        linkTag.setLong("linkPos", pos.toLong());
        linkTag.setInteger("linkDim", dimension);
        stack.setTagInfo("mirrorLink", linkTag);
    }

    public static void removeLink(ItemStack stack) {
        stack.removeSubCompound("mirrorLink");
    }

    public static BlockPos getLinkedPosition(ItemStack stack) {
        if(hasMirroredInfusion(stack) && isLinked(stack) && stack.getTagCompound() != null) {
            return BlockPos.fromLong(stack.getTagCompound().getCompoundTag("mirrorLink").getLong("linkPos"));
        }
        return new BlockPos(0,0,0);
    }

    public static int getLinkedDimension(ItemStack stack) {
        if(hasMirroredInfusion(stack) && isLinked(stack) && stack.getTagCompound() != null) {
            return stack.getTagCompound().getCompoundTag("mirrorLink").getInteger("linkDim");
        }
        return 0;
    }

    public static String getLinkedDimensionName(ItemStack stack) {
       return StringHelper.getDimensionName(getLinkedDimension(stack));
    }

    public static boolean attemptTransfer(ItemStack toolStack, NonNullList<ItemStack> transferStacks) {
        int dimId = getLinkedDimension(toolStack);
        World targetWorld = DimensionManager.getWorld(dimId);
        if(targetWorld == null) {
            DimensionManager.initDimension(dimId);
            targetWorld = DimensionManager.getWorld(dimId);
        }
        if(targetWorld != null) {
            BlockPos pos = getLinkedPosition(toolStack);
            TileEntity tile = targetWorld.getTileEntity(pos);
            if(tile instanceof TileMirror) {
                for(ItemStack transerStack : transferStacks) {
                    if(!transerStack.isEmpty()) {
                        ((TileMirror) tile).addStack(transerStack.copy());
                    }
                }
                return true;
            } else {
                removeLink(toolStack);
                //TODO: zap sound effect and error message (clientside - see ItemHandMirror)
            }
        }
        return false;
    }
}
