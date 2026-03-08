package mod.emt.planarartifice.enchants;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.registry.ModEnchantsPA;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

@Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID)
public class InfusionEnchantmentMetaphize {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onXpDropped(LivingExperienceDropEvent event) {
        EntityPlayer player = event.getAttackingPlayer();
        EntityLivingBase target = event.getEntityLiving();
        if(player != null && !player.world.isRemote && target.recentlyHit > 0) {
            ItemStack heldStack = player.getHeldItemMainhand();
            int level = EnumInfusionEnchantment.getInfusionEnchantmentLevel(heldStack, ModEnchantsPA.METAPHIZE);
            if(level > 0) {
                float vis = (float) event.getDroppedExperience() * (level * 0.3f);
                if(vis > 0) {
                    float baseVis = AuraHelper.getAuraBase(player.world, target.getPosition());
                    float chunkVis = AuraHelper.getVis(player.world, target.getPosition());
                    float chunkFlux = AuraHelper.getFlux(player.world, target.getPosition());
                    if((chunkVis + chunkFlux) < (baseVis * 1.1f)) {
                        AuraHelper.addVis(target.world, target.getPosition(), vis);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
