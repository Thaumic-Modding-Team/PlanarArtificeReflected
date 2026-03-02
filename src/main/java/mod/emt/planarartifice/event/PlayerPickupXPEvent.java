package mod.emt.planarartifice.event;

import baubles.api.BaublesApi;
import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.registry.ModItemsPA;
import mod.emt.planarartifice.utils.helper.ResearchHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.research.ResearchCategory;

@Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID)
public class PlayerPickupXPEvent {
    @SubscribeEvent
    public static void pickupXP(PlayerPickupXpEvent event) {
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
}
