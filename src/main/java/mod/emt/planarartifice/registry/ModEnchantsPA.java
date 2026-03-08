package mod.emt.planarartifice.registry;

import com.invadermonky.thaumicapi.api.ThaumicAPI;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

public class ModEnchantsPA {
    //TODO: Change required research.
    public static EnumInfusionEnchantment MIRRORED = ThaumicAPI.registerInfusionEnchantment(
            "MIRRORED", 1, "INFUSIONENCHANTMENT", "weapon", "pickaxe", "shovel", "hoe", "axe");

    //TODO: Change required research.
    public static EnumInfusionEnchantment METAPHIZE = ThaumicAPI.registerInfusionEnchantment(
            "METAPHIZE", 3, "INFUSIONENCHANTMENT", "weapon");
}
