package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID)
public class ModRegistryPA {
    @SubscribeEvent
    public static void registerBlocks(@Nonnull final RegistryEvent.Register<Block> event) {
        ModBlocksPA.registerBlocks(event);
    }

    @SubscribeEvent
    public static void registerItems(@Nonnull final RegistryEvent.Register<Item> event) {
        ModBlocksPA.registerBlockItems(event);
        ModItemsPA.registerItems(event);
    }

    @SubscribeEvent
    public static void registerRecipes(@Nonnull final RegistryEvent.Register<IRecipe> event) {
        ModRecipesPA.registerRecipes();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModItemsPA.registerItemModels(event);
        ModBlocksPA.registerBlockModels(event);
    }
}
