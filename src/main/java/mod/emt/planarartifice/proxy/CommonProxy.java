package mod.emt.planarartifice.proxy;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.compat.PACompatHandler;
import mod.emt.planarartifice.item.bauble.PAItemAuraMeter;
import mod.emt.planarartifice.registry.ModRecipesPA;
import net.minecraft.util.ResourceLocation;
import thaumcraft.Thaumcraft;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;

public class CommonProxy {
    public void preInit() {
    }

    public void init() {
        this.registerResearch();
        ModRecipesPA.registerRecipes();
        PACompatHandler.init();

        PAItemAuraMeter.GUI_ITEMS.add(ItemsTC.sanityChecker);
        PAItemAuraMeter.GUI_ITEMS.add(ItemsTC.thaumometer);
    }

    public void postInit() {
    }

    private void registerResearch() {
        ResearchCategories.registerCategory(
                "PLANAR_ARTIFICE", "FIRSTSTEPS", new AspectList(),
                new ResourceLocation(PlanarArtifice.MOD_ID, "textures/research/logo_icon.png"),
                new ResourceLocation(PlanarArtifice.MOD_ID, "textures/gui/research_background.jpg"),
                new ResourceLocation(Thaumcraft.MODID, "textures/gui/gui_research_back_over.png"));

        ThaumcraftApi.registerResearchLocation(new ResourceLocation(PlanarArtifice.MOD_ID, "research/alkimium"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(PlanarArtifice.MOD_ID, "research/basics"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(PlanarArtifice.MOD_ID, "research/bismuth"));
    }
}
