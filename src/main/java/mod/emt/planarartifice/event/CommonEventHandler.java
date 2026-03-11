package mod.emt.planarartifice.event;

import baubles.api.BaublesApi;
import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.registry.ModItemsPA;
import mod.emt.planarartifice.tile.TileFlawlessMirror;
import mod.emt.planarartifice.utils.helper.ResearchHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.common.lib.SoundsTC;

@Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID)
public class CommonEventHandler {
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
}
