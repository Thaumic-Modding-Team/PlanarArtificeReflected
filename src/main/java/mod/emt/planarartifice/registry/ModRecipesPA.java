package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
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
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

@SuppressWarnings("ConstantConditions")
public class ModRecipesPA {
    private static ResourceLocation defaultGroup = new ResourceLocation("");

    public static void registerRecipes() {
        registerArcaneCraftingRecipes();
        registerCrucibleRecipes();
        registerInfusionRecipes();
    }

    public static void registerOreDicts() {
        OreDictionary.registerOre("blockAlkimium", ModBlocksPA.ALKIMIUM_BLOCK);
        OreDictionary.registerOre("blockBismuth", ModBlocksPA.BISMUTH_BLOCK);

        OreDictionary.registerOre("ingotAlkimium", ModItemsPA.ALKIMIUM_INGOT);
        OreDictionary.registerOre("nuggetAlkimium", ModItemsPA.ALKIMIUM_NUGGET);
        OreDictionary.registerOre("plateAlkimium", ModItemsPA.ALKIMIUM_PLATE);
        OreDictionary.registerOre("ingotBismuth", ModItemsPA.BISMUTH_INGOT);
        OreDictionary.registerOre("nuggetBismuth", ModItemsPA.BISMUTH_NUGGET);
        OreDictionary.registerOre("plateBismuth", ModItemsPA.BISMUTH_PLATE);
    }

    private static void registerArcaneCraftingRecipes() {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "alkimic_construct"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_ALKIMIUM_APPLICATIONS@2",
                100,
                new AspectList().add(Aspect.WATER, 2).add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
                new ItemStack(ModBlocksPA.ALKIMIC_CONSTRUCT, 2),
                "AVA",
                "TPT",
                "AVA",
                'A', "plateAlkimium",
                'V', new ItemStack(BlocksTC.tubeValve),
                'T', new ItemStack(BlocksTC.tube),
                'P', new ItemStack(BlocksTC.plankSilverwood)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_centrifuge"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_ALKIMIUM_CENTRIFUGE",
                125,
                new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
                ModBlocksPA.ALKIMIUM_CENTRIFUGE,
                " T ",
                "M*A",
                " T ",
                'T', new ItemStack(BlocksTC.tube),
                'M', new ItemStack(ItemsTC.morphicResonator),
                '*', new ItemStack(ModBlocksPA.ALKIMIC_CONSTRUCT),
                'A', new ItemStack(ItemsTC.mechanismComplex)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_smeltery"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_ALKIMIUM_APPLICATIONS@2",
                75,
                new AspectList().add(Aspect.FIRE, 2),
                ModBlocksPA.ALKIMIUM_SMELTERY,
                "ACA",
                "#F#",
                "#*#",
                'A', "plateAlkimium",
                'C', new ItemStack(BlocksTC.crucible),
                '#', "cobblestone",
                'F', new ItemStack(Blocks.FURNACE),
                '*', new ItemStack(ModBlocksPA.ALKIMIC_CONSTRUCT)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_smeltery_thaumium"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_ALKIMIUM_THAUMIUM_SMELTERY",
                300,
                new AspectList().add(Aspect.FIRE, 4),
                ModBlocksPA.ALKIMIUM_SMELTERY_THAUMIUM,
                "A#A",
                "T*T",
                "TTT",
                'A', "plateAlkimium",
                '#', new ItemStack(ModBlocksPA.ALKIMIUM_SMELTERY),
                'T', "plateThaumium",
                '*', new ItemStack(ModBlocksPA.ALKIMIC_CONSTRUCT)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_smeltery_void"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_ALKIMIUM_VOID_SMELTERY@2",
                800,
                new AspectList().add(Aspect.FIRE, 6),
                ModBlocksPA.ALKIMIUM_SMELTERY_VOID,
                "A#A",
                "T*T",
                "TTT",
                'A', "plateAlkimium",
                '#', new ItemStack(ModBlocksPA.ALKIMIUM_SMELTERY),
                'T', "plateVoid",
                '*', new ItemStack(BlocksTC.metalAlchemicalAdvanced)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_smelter_aux"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_ALKIMIUM_DISTILLATION",
                125,
                new AspectList().add(Aspect.AIR, 2).add(Aspect.EARTH, 2),
                ModBlocksPA.ALKIMIUM_SMELTER_AUX,
                "STS",
                "A*A",
                "I#I",
                'S', new ItemStack(BlocksTC.plankSilverwood),
                'T', new ItemStack(BlocksTC.tubeFilter),
                'A', "plateAlkimium",
                '*', new ItemStack(ModBlocksPA.ALKIMIC_CONSTRUCT),
                'I', "plateIron",
                '#', new ItemStack(BlocksTC.bellows)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_smelter_vent"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_ALKIMIUM_DISTILLATION",
                175,
                new AspectList().add(Aspect.AIR, 2),
                ModBlocksPA.ALKIMIUM_SMELTER_VENT,
                "IAI",
                "F*F",
                "IAI",
                'I', "plateIron",
                'A', "plateAlkimium",
                'F', new ItemStack(ItemsTC.filter),
                '*', new ItemStack(ModBlocksPA.ALKIMIC_CONSTRUCT)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "aura_meter"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_BISMUTH",
                25,
                new AspectList().add(Aspect.AIR, 1).add(Aspect.EARTH, 1).add(Aspect.ENTROPY, 1).add(Aspect.FIRE, 1).add(Aspect.ORDER, 1).add(Aspect.WATER, 1),
                ModItemsPA.AURA_METER,
                "B",
                "M",
                "B",
                'B', "plateBismuth",
                'M', new ItemStack(ItemsTC.mirroredGlass)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "bismuth_caster"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_BISMUTH_CASTER",
                250,
                new AspectList().add(Aspect.AIR, 2).add(Aspect.EARTH, 2).add(Aspect.ENTROPY, 2).add(Aspect.FIRE, 2).add(Aspect.ORDER, 2).add(Aspect.WATER, 2),
                ModItemsPA.BISMUTH_CASTER,
                "BBB",
                "MFM",
                "MRM",
                'B', "plateBismuth",
                'M', new ItemStack(ItemsTC.fabric),
                'F', new ItemStack(ItemsTC.filter),
                'R', new ItemStack(ItemsTC.morphicResonator)));
        ItemStack claymoreStack = new ItemStack(ModItemsPA.BISMUTH_CLAYMORE);
        EnumInfusionEnchantment.addInfusionEnchantment(claymoreStack, ModEnchantsPA.METAPHIZE, 2);
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "bismuth_claymore"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_BISMUTH",
                50,
                new AspectList().add(Aspect.AIR, 1).add(Aspect.EARTH, 1).add(Aspect.ENTROPY, 1).add(Aspect.FIRE, 1).add(Aspect.ORDER, 1).add(Aspect.WATER, 1),
                claymoreStack,
                " B ",
                "BBB",
                " T ",
                'B', "ingotBismuth",
                'T', "ingotThaumium"));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "mirrored_jar"), new ShapedArcaneRecipe(
                defaultGroup,
                "PA_MIRRORED_JAR",
                50,
                new AspectList().add(Aspect.ORDER, 1),
                new ItemStack(ModBlocksPA.MIRRORED_JAR),
                "LJL",
                "LML",
                " S ",
                'L', new ItemStack(BlocksTC.logGreatwood),
                'J', new ItemStack(BlocksTC.jarNormal),
                'M', new ItemStack(BlocksTC.mirrorEssentia),
                'S', "slimeball"));
    }

    private static void registerCrucibleRecipes() {
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
                new AspectList().add(Aspect.MAGIC, 30).add(Aspect.LIFE, 30).add(Aspect.BEAST, 20).add(Aspect.EARTH, 20)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "THAUMIC_APPLE_ENCHANTED"), new CrucibleRecipe(
                "PA_THAUMIC_APPLE",
                new ItemStack(ModItemsPA.THAUMATURGES_FRUIT_ENCHANTED),
                new ItemStack(Items.GOLDEN_APPLE, 1, 1),
                new AspectList().add(Aspect.MAGIC, 75).add(Aspect.LIFE, 75).add(Aspect.BEAST, 50).add(Aspect.EARTH, 50)));
    }

    private static void registerInfusionRecipes() {
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "alchemical_glass_cutter"),
                new InfusionRecipe("PA_ALCHEMICAL_GLASS_CUTTER", new ItemStack(ModItemsPA.GLASS_CUTTER_VIS), 2,
                        new AspectList().add(Aspect.AURA, 25).add(Aspect.CRYSTAL, 25),
                        new ItemStack(ModItemsPA.GLASS_CUTTER, 1, OreDictionary.WILDCARD_VALUE),
                        "gemAmber",
                        "ingotAlkimium",
                        "gemAmber",
                        "ingotAlkimium"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_goggles"),
                new InfusionRecipe("PA_ALKIMIUM_GOGGLES", new ItemStack(ModItemsPA.ALKIMIUM_GOGGLES), 4,
                        new AspectList().add(Aspect.ALCHEMY, 75).add(Aspect.AURA, 50).add(Aspect.MAGIC, 50).add(Aspect.MIND, 75),
                        new ItemStack(ItemsTC.goggles, 1, OreDictionary.WILDCARD_VALUE),
                        "quicksilver",
                        "plateAlkimium",
                        "quicksilver",
                        "plateAlkimium",
                        new ItemStack(ItemsTC.resonator),
                        "plateAlkimium",
                        "quicksilver",
                        "plateAlkimium"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "flawless_focus"),
                new InfusionRecipe("PA_FLAWLESS_FOCI", new ItemStack(ModItemsPA.FLAWLESS_FOCUS), 10,
                        new AspectList().add(Aspect.AURA, 150).add(Aspect.MAGIC, 50).add(Aspect.ORDER, 100).add(Aspect.VOID, 150),
                        new ItemStack(ItemsTC.primordialPearl),
                        "blockBismuth",
                        new ItemStack(ItemsTC.focus3),
                        "blockBismuth",
                        new ItemStack(Items.NETHER_STAR)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "flawless_essentia_mirror"),
                new InfusionRecipe("PA_FLAWLESS_MIRRORS", new ItemStack(ModBlocksPA.FLAWLESS_MIRROR_ESSENTIA), 9,
                        new AspectList().add(Aspect.EXCHANGE, 75).add(Aspect.MOTION, 75).add(Aspect.VOID, 75).add(Aspect.WATER, 75),
                        new ItemStack(BlocksTC.mirrorEssentia),
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        "plateAlkimium",
                        "plateAlkimium",
                        "plateBismuth",
                        "plateBismuth"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "flawless_mirror"),
                new InfusionRecipe("PA_FLAWLESS_MIRRORS", new ItemStack(ModBlocksPA.FLAWLESS_MIRROR), 9,
                        new AspectList().add(Aspect.DARKNESS, 75).add(Aspect.EXCHANGE, 75).add(Aspect.MOTION, 75).add(Aspect.VOID, 75),
                        new ItemStack(BlocksTC.mirror),
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        "plateAlkimium",
                        "plateAlkimium",
                        "plateBismuth",
                        "plateBismuth"));
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
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "mirrored_amulet"),
                new InfusionRecipe("PA_MIRRORED_AMULET", new ItemStack(ModItemsPA.MIRRORED_AMULET), 6,
                        new AspectList().add(Aspect.ELDRITCH, 40).add(Aspect.EXCHANGE, 40).add(Aspect.VOID, 40),
                        new ItemStack(ItemsTC.baubles, 1, 4),
                        new ItemStack(BlocksTC.mirror),
                        "enderpearl"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "starving_chest_1"),
                new InfusionRecipe("PA_STARVING_CHEST", new ItemStack(ModBlocksPA.STARVING_CHEST_SMALL), 3,
                        new AspectList().add(Aspect.MOTION, 25).add(Aspect.TRAP, 25).add(Aspect.VOID, 25),
                        new ItemStack(BlocksTC.hungryChest),
                        new ItemStack(ItemsTC.filter),
                        "plateBismuth",
                        new ItemStack(Blocks.HOPPER),
                        "plateBismuth"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "starving_chest_2"),
                new InfusionRecipe("PA_STARVING_CHEST", new ItemStack(ModBlocksPA.STARVING_CHEST_MEDIUM), 6,
                        new AspectList().add(Aspect.MOTION, 50).add(Aspect.TRAP, 50).add(Aspect.VOID, 50),
                        new ItemStack(ModBlocksPA.STARVING_CHEST_SMALL),
                        new ItemStack(ItemsTC.filter),
                        "plateBismuth",
                        new ItemStack(Blocks.HOPPER),
                        "plateBismuth"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "starving_chest_3"),
                new InfusionRecipe("PA_STARVING_CHEST", new ItemStack(ModBlocksPA.STARVING_CHEST_LARGE), 9,
                        new AspectList().add(Aspect.MOTION, 75).add(Aspect.TRAP, 75).add(Aspect.VOID, 75),
                        new ItemStack(ModBlocksPA.STARVING_CHEST_MEDIUM),
                        new ItemStack(ItemsTC.filter),
                        "plateBismuth",
                        new ItemStack(Blocks.HOPPER),
                        "plateBismuth"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "starving_chest_4"),
                new InfusionRecipe("PA_STARVING_CHEST", new ItemStack(ModBlocksPA.STARVING_CHEST_HUGE), 12,
                        new AspectList().add(Aspect.MOTION, 100).add(Aspect.TRAP, 100).add(Aspect.VOID, 100),
                        new ItemStack(ModBlocksPA.STARVING_CHEST_LARGE),
                        new ItemStack(ItemsTC.filter),
                        "plateBismuth",
                        new ItemStack(Blocks.HOPPER),
                        "plateBismuth"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(PlanarArtifice.MOD_ID, "suspension_belt"),
                new InfusionRecipe("PA_SUSPENSION_BELT", new ItemStack(ModItemsPA.SUSPENSION_BELT), 6,
                        new AspectList().add(Aspect.AIR, 75).add(Aspect.AURA, 50).add(Aspect.ENERGY, 65)
                                .add(Aspect.FLIGHT, 125).add(Aspect.MAGIC, 50).add(Aspect.MOTION, 75),
                        new ItemStack(ItemsTC.baubles, 1, 6),
                        "feather",
                        new ItemStack(ItemsTC.ringCloud),
                        new ItemStack(Items.SUGAR),
                        new ItemStack(ItemsTC.alumentum),
                        new ItemStack(BlocksTC.levitator),
                        new ItemStack(BlocksTC.pavingStoneBarrier),
                        new ItemStack(Blocks.PISTON),
                        "oreCrystalAir"));

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
