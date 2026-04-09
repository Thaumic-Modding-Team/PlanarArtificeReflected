package mod.emt.planarartifice.registry;

import com.invadermonky.thaumicapi.api.ThaumicAPI;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

public class ModEnchantsPA {
    public static EnumInfusionEnchantment MIRRORED = ThaumicAPI.registerInfusionEnchantment(
            "MIRRORED", 1, "PA_PLANAR_INFUSION_ENCHANTMENT", "weapon", "pickaxe", "shovel", "hoe", "axe");

    public static EnumInfusionEnchantment METAPHIZE = ThaumicAPI.registerInfusionEnchantment(
            "METAPHIZE", 3, "PA_PLANAR_INFUSION_ENCHANTMENT", "weapon");
}
