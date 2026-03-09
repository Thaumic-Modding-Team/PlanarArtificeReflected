package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.block.BlockAlkimiumSmelteryPA;
import mod.emt.planarartifice.block.BlockMaterialPA;
import mod.emt.planarartifice.tile.TileAlkimiumSmelterPA;
import mod.emt.planarartifice.utils.helper.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID)
@GameRegistry.ObjectHolder(PlanarArtifice.MOD_ID)
public class ModBlocksPA {
    public static final BlockMaterialPA ALKIMIC_CONSTRUCT = null;
    public static final BlockMaterialPA ALKIMIUM_BLOCK = null;
    public static final BlockAlkimiumSmelteryPA ALKIMIUM_SMELTERY = null;
    public static final BlockMaterialPA BISMUTH_BLOCK = null;

    @SubscribeEvent
    public static void registerBlocks(@Nonnull final RegistryEvent.Register<Block> event) {
        LogHelper.info("Registering blocks...");

        final IForgeRegistry<Block> registry = event.getRegistry();

        registry.registerAll(
                ModRegistryPA.setup(new BlockMaterialPA(Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, true), "alkimium_block"),
                ModRegistryPA.setup(new BlockMaterialPA(Material.IRON, MapColor.SILVER, 5.0F, 15.0F, SoundType.METAL, true), "bismuth_block"),
                ModRegistryPA.setup(new BlockMaterialPA(Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, false), "alkimic_construct"),
                ModRegistryPA.setup(new BlockAlkimiumSmelteryPA(14, 0.85F, 375), "alkimium_smeltery")
        );

        GameRegistry.registerTileEntity(TileAlkimiumSmelterPA.class, new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_smeltery"));
    }
}
