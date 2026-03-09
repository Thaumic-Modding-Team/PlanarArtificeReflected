package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.IngredientNBTTC;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.crafting.InfusionEnchantmentRecipe;

public class ModRecipesPA {
    private static ResourceLocation defaultGroup = new ResourceLocation("");

    public static void registerRecipes() {
        registerArcaneCraftingRecipes();
        registerCrucibleRecipes();
        registerInfusionRecipes();
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
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "ALKIMIUM_INGOT"), new CrucibleRecipe(
                "PA_ALKIMIUM@1",
                new ItemStack(ModItemsPA.ALKIMIUM_INGOT),
                "ingotBrass",
                new AspectList().add(Aspect.ALCHEMY, 10).add(Aspect.ORDER, 10)));
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
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "THAUMIC_APPLE"), new CrucibleRecipe(
                "PA_THAUMIC_APPLE",
                new ItemStack(ModItemsPA.THAUMATURGES_FRUIT),
                new ItemStack(Items.GOLDEN_APPLE, 1, 0),
                new AspectList().add(Aspect.MAGIC, 75).add(Aspect.LIFE, 75).add(Aspect.BEAST, 50).add(Aspect.EARTH, 50)));
    }

    public static void registerInfusionRecipes() {
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "mirromirous_headband"),
                new InfusionRecipe("PA_MIRROMIROUS_HEADBAND", new ItemStack(ModItemsPA.MIRROMIROUS_HEADBAND), 7,
                        new AspectList().add(Aspect.MIND, 175).add(Aspect.CRYSTAL, 100).add(Aspect.TRAP, 125),
                        new ItemStack(ItemsTC.bandCuriosity),
                        "plateBismuth",
                        new ItemStack(Items.ENCHANTED_BOOK),
                        "plateBismuth",
                        new ItemStack(Items.ENCHANTED_BOOK),
                        "plateBismuth",
                        new ItemStack(Items.ENCHANTED_BOOK),
                        "plateBismuth",
                        new ItemStack(Items.ENCHANTED_BOOK)));

        InfusionEnchantmentRecipe mirroredInfusion = new InfusionEnchantmentRecipe(
                ModEnchantsPA.MIRRORED,
                new AspectList().add(Aspect.MOTION, 80).add(Aspect.TOOL, 150),
                new IngredientNBTTC(new ItemStack(Items.ENCHANTED_BOOK)),
                Ingredient.fromItem(ItemsTC.handMirror));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "mirrored_infusion"), mirroredInfusion);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "mirrored_infusion_fake1"), new InfusionEnchantmentRecipe(mirroredInfusion, new ItemStack(Items.WOODEN_SWORD)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "mirrored_infusion_fake2"), new InfusionEnchantmentRecipe(mirroredInfusion, new ItemStack(Items.WOODEN_PICKAXE)));

        InfusionEnchantmentRecipe metaphizeInfusion = new InfusionEnchantmentRecipe(
                ModEnchantsPA.METAPHIZE,
                new AspectList().add(Aspect.AURA, 50).add(Aspect.ENERGY, 80),
                new IngredientNBTTC(new ItemStack(Items.ENCHANTED_BOOK)),
                Ingredient.fromItem(Item.getItemFromBlock(BlocksTC.jarBrain)),
                Ingredient.fromItem(ItemsTC.visResonator)
        );
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "metaphize_infusion"), metaphizeInfusion);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "metaphize_infusion_fake"), new InfusionEnchantmentRecipe(metaphizeInfusion, new ItemStack(Items.WOODEN_SWORD)));
    }
}
