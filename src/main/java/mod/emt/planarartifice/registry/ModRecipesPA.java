package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;

public class ModRecipesPA {
    private static ResourceLocation defaultGroup = new ResourceLocation("");

    public static void registerRecipes() {
        registerArcaneCraftingRecipes();
        registerCrucibleRecipes();
    }

    public static void registerArcaneCraftingRecipes() {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "aura_meter"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_BISMUTH",
                25,
                new AspectList().add(Aspect.AIR, 1).add(Aspect.EARTH, 1).add(Aspect.ENTROPY, 1).add(Aspect.FIRE, 1).add(Aspect.ORDER, 1).add(Aspect.WATER, 1),
                ModItemsPA.AURA_METER,
                "B",
                "M",
                "B",
                'B', new ItemStack(ModItemsPA.BISMUTH_PLATE),
                'M', new ItemStack(ItemsTC.mirroredGlass)));
    }

    public static void registerCrucibleRecipes() {
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "BISMUTH_INGOT"), new CrucibleRecipe(
                "PA_BISMUTH@1",
                new ItemStack(ModItemsPA.BISMUTH_INGOT),
                new ItemStack(ItemsTC.ingots, 1, 0),
                new AspectList().add(Aspect.AURA, 20).add(Aspect.ENERGY, 20)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "BISMUTH_TO_GOLD"), new CrucibleRecipe(
                "PA_METAL_TRANSMUTATION",
                new ItemStack(Items.GOLD_INGOT),
                new ItemStack(ModItemsPA.BISMUTH_INGOT),
                new AspectList().add(Aspect.DESIRE, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "BISMUTH_TO_IRON"), new CrucibleRecipe(
                "PA_METAL_TRANSMUTATION",
                new ItemStack(Items.IRON_INGOT),
                new ItemStack(ModItemsPA.BISMUTH_INGOT),
                new AspectList().add(Aspect.METAL, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "BISMUTH_TO_QUICKSILVER"), new CrucibleRecipe(
                "PA_METAL_TRANSMUTATION",
                new ItemStack(ItemsTC.quicksilver),
                new ItemStack(ModItemsPA.BISMUTH_INGOT),
                new AspectList().add(Aspect.ALCHEMY, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "EM_SALIS_MUNDUS"), new CrucibleRecipe(
                "PA_ESSENTIA_MANIPULATION",
                new ItemStack(ItemsTC.salisMundus),
                new ItemStack(Items.REDSTONE),
                new AspectList().add(Aspect.MAGIC, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "RT_AMBER"), new CrucibleRecipe(
                "PA_REAGENT_TRANSMUTATION",
                new ItemStack(ItemsTC.amber),
                new ItemStack(ItemsTC.quicksilver),
                new AspectList().add(Aspect.TRAP, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "RT_BLAZE_POWDER"), new CrucibleRecipe(
                "PA_REAGENT_TRANSMUTATION@2",
                new ItemStack(Items.BLAZE_POWDER),
                new ItemStack(Items.GUNPOWDER),
                new AspectList().add(Aspect.MAGIC, 5).add(Aspect.FIRE, 5)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "RT_ENDER_EYE"), new CrucibleRecipe(
                "PA_REAGENT_TRANSMUTATION@1",
                new ItemStack(Items.ENDER_EYE),
                new ItemStack(Items.SPIDER_EYE),
                new AspectList().add(Aspect.CRYSTAL, 10).add(Aspect.FIRE, 10).add(Aspect.ELDRITCH, 20)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "RT_QUICKSILVER"), new CrucibleRecipe(
                "PA_REAGENT_TRANSMUTATION",
                new ItemStack(ItemsTC.quicksilver),
                new ItemStack(ItemsTC.amber),
                new AspectList().add(Aspect.ALCHEMY, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "RT_REDSTONE"), new CrucibleRecipe(
                "PA_REAGENT_TRANSMUTATION@1",
                new ItemStack(Items.REDSTONE),
                new ItemStack(Items.GUNPOWDER),
                new AspectList().add(Aspect.ENERGY, 5)));
    }
}
