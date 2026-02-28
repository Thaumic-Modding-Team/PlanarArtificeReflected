package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.blocks.PABlockMaterial;
import mod.emt.planarartifice.utils.helpers.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID)
@GameRegistry.ObjectHolder(PlanarArtifice.MOD_ID)
public class ModBlocksPA {
    public static final PABlockMaterial BISMUTH_BLOCK = null;

    @SubscribeEvent
    public static void registerBlocks(@Nonnull final RegistryEvent.Register<Block> event) {
        LogHelper.info("Registering blocks...");

        final IForgeRegistry<Block> registry = event.getRegistry();

        registry.registerAll(
                ModRegistryPA.setup(new PABlockMaterial(Material.IRON, MapColor.SILVER, 5.0F, 15.0F, SoundType.METAL, true), "bismuth_block")
        );
    }
}
