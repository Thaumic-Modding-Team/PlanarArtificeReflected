package mod.emt.planarartifice.proxy;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.compat.CompatHandlerPA;
import mod.emt.planarartifice.item.bauble.ItemAuraMeter;
import mod.emt.planarartifice.registry.ModGuiHandlerPA;
import mod.emt.planarartifice.registry.ModItemsPA;
import mod.emt.planarartifice.registry.ModRecipesPA;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thaumcraft.Thaumcraft;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemMaterial;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;

public class CommonProxy {
    public void preInit() {
    }

    public void init() {
        this.registerGolemMaterials();
        this.registerResearch();
        ModRecipesPA.registerOreDicts();
        CompatHandlerPA.init();

        ItemAuraMeter.GUI_ITEMS.add(ItemsTC.sanityChecker);
        ItemAuraMeter.GUI_ITEMS.add(ItemsTC.thaumometer);
    }

    public void postInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(PlanarArtifice.instance, new ModGuiHandlerPA());
    }

    private void registerGolemMaterials() {
        GolemMaterial.register(
                new GolemMaterial("PA_ALKIMIUM", new String[]{"PA_GOLEM_MAT_ALKIMIUM"}, new ResourceLocation(PlanarArtifice.MOD_ID, "textures/entity/golem/mat_alkimium.png"),
                        5035138, 13, 12, 4, // [Color, Health, Armor , Damage] - [1 = 0.5]
                        new ItemStack(ModItemsPA.ALKIMIUM_PLATE), new ItemStack(ItemsTC.mechanismSimple), // Base Component, Base Mechanism
                        new EnumGolemTrait[]{EnumGolemTrait.LIGHT, EnumGolemTrait.FIREPROOF, EnumGolemTrait.BLASTPROOF, EnumGolemTrait.FRAGILE} // Starting Traits
                )
        );
    }

    private void registerResearch() {
        ResearchCategories.registerCategory(
                "PLANAR_ARTIFICE", "FIRSTSTEPS", new AspectList(),
                new ResourceLocation(PlanarArtifice.MOD_ID, "textures/misc/logo_icon.png"),
                new ResourceLocation(PlanarArtifice.MOD_ID, "textures/gui/research_background.jpg"),
                new ResourceLocation(Thaumcraft.MODID, "textures/gui/gui_research_back_over.png"));

        ThaumcraftApi.registerResearchLocation(new ResourceLocation(PlanarArtifice.MOD_ID, "research/alkimium"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(PlanarArtifice.MOD_ID, "research/basics"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(PlanarArtifice.MOD_ID, "research/bismuth"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(PlanarArtifice.MOD_ID, "research/glasswork"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(PlanarArtifice.MOD_ID, "research/mirromirous"));
    }
}
