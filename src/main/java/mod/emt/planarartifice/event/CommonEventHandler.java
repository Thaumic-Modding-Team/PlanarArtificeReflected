package mod.emt.planarartifice.event;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.item.bauble.ItemMirroredAmulet;
import mod.emt.planarartifice.registry.ModItemsPA;
import mod.emt.planarartifice.tile.TileFlawlessMirror;
import mod.emt.planarartifice.utils.helper.PlayerHelper;
import mod.emt.planarartifice.utils.helper.ResearchHelper;
import mod.emt.planarartifice.utils.helper.WorldHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.tiles.devices.TileMirror;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID)
public class CommonEventHandler {
    private static final Map<UUID, ItemStack> PLAYER_AMULETS = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerPickupXp(PlayerPickupXpEvent event) {
        if (event.getEntityPlayer() != null && !event.getEntityPlayer().world.isRemote && BaublesApi.isBaubleEquipped(event.getEntityPlayer(), ModItemsPA.MIRROMIROUS_HEADBAND) >= 0 && event.getOrb().getXpValue() > 1) {
            int d = event.getOrb().xpValue / 2;
            EntityXPOrb orb = event.getOrb();
            orb.xpValue -= d;
            float r = event.getEntityPlayer().getRNG().nextFloat();

            if (r < 0.1D * d) {
                ResearchCategory[] categories = ResearchHelper.getResearchCategories();
                ThaumcraftApi.internalMethods.addKnowledge(event.getEntityPlayer(), IPlayerKnowledge.EnumKnowledgeType.THEORY, categories[event.getEntityPlayer().getRNG().nextInt(categories.length)], 2);
            } else if (r < 0.4D * d) {
                ResearchCategory[] categories = ResearchHelper.getResearchCategories();
                ThaumcraftApi.internalMethods.addKnowledge(event.getEntityPlayer(), IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, categories[event.getEntityPlayer().getRNG().nextInt(categories.length)], 2);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack heldItem = player.getHeldItem(event.getHand());
        World world = player.world;
        BlockPos pos = event.getPos();
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileFlawlessMirror && heldItem.getItem() == ItemsTC.handMirror) {
            if(!world.isRemote) {
                heldItem.setTagInfo("linkX", new NBTTagInt(pos.getX()));
                heldItem.setTagInfo("linkY", new NBTTagInt(pos.getY()));
                heldItem.setTagInfo("linkZ", new NBTTagInt(pos.getZ()));
                heldItem.setTagInfo("linkDim", new NBTTagInt(world.provider.getDimension()));
                player.sendMessage(new TextComponentTranslation("tc.handmirrorlinked"));
                player.inventoryContainer.detectAndSendChanges();
            } else {
                world.playSound(player, pos.getX(), pos.getY(), pos.getZ(), SoundsTC.jar, SoundCategory.BLOCKS, 1.0F, 2.0F);
                player.swingArm(EnumHand.MAIN_HAND);
            }
            event.setCancellationResult(EnumActionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerDeath(LivingDeathEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
            for(int slot = 0; slot < handler.getSlots(); slot++) {
                ItemStack slotStack = handler.getStackInSlot(slot);
                if(slotStack.getItem() instanceof ItemMirroredAmulet && ((ItemMirroredAmulet) slotStack.getItem()).hasLinkTagInfo(slotStack)) {
                    PLAYER_AMULETS.put(PlayerHelper.getUUIDFromPlayer(player), slotStack.copy());
                    break;
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerDrops(PlayerDropsEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        UUID playerId = PlayerHelper.getUUIDFromPlayer(player);
        ItemStack amulet = PLAYER_AMULETS.getOrDefault(playerId, ItemStack.EMPTY);
        if(!amulet.isEmpty() && amulet.getTagCompound() != null) {
            NBTTagCompound tag = amulet.getTagCompound();
            BlockPos pos = new BlockPos(tag.getInteger("linkX"), tag.getInteger("linkY"), tag.getInteger("linkZ"));
            int dimId = tag.getInteger("linkDim");
            TileEntity tile = WorldHelper.getWorldTileEntity(dimId, pos, true);
            if (tile instanceof TileMirror) {
                TileMirror mirror = (TileMirror) tile;
                Iterator<EntityItem> iterator = event.getDrops().iterator();
                while (iterator.hasNext()) {
                    ItemStack stack = iterator.next().getItem();
                    if (!stack.isEmpty()) {
                        mirror.addStack(stack.copy());
                    }
                    iterator.remove();
                }
                if(event.getDrops().isEmpty()) {
                    event.setCanceled(true);
                }
            }
        }
        PLAYER_AMULETS.remove(playerId);
    }
}
