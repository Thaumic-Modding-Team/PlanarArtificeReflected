package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.block.BlockMaterialPA;
import mod.emt.planarartifice.block.BlockSmelterPA;
import mod.emt.planarartifice.tile.TileSmelterPA;
import mod.emt.planarartifice.utils.helper.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@GameRegistry.ObjectHolder(PlanarArtifice.MOD_ID)
public class ModBlocksPA {
    public static final BlockMaterialPA ALKIMIC_CONSTRUCT = null;
    public static final BlockMaterialPA ALKIMIUM_BLOCK = null;
    public static final BlockSmelterPA ALKIMIUM_SMELTERY = null;
    public static final BlockMaterialPA BISMUTH_BLOCK = null;

    public static void registerBlocks(@Nonnull final RegistryEvent.Register<Block> event) {
        LogHelper.info("Registering blocks...");

        final IForgeRegistry<Block> registry = event.getRegistry();

        registry.registerAll(
                new BlockMaterialPA("alkimium_block", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, true),
                new BlockMaterialPA("bismuth_block", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, true),
                new BlockMaterialPA("alkimic_construct", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, false),
                new BlockSmelterPA("alkimium_smeltery", 14, 0.85F, 375)
        );

        registerTileEntities();
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileSmelterPA.class, new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_smeltery"));
    }

    @SuppressWarnings("ConstantConditions")
    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.registerAll(
                new ItemBlock(ALKIMIUM_BLOCK).setRegistryName(ALKIMIUM_BLOCK.getRegistryName()).setTranslationKey(ALKIMIUM_BLOCK.getTranslationKey()).setCreativeTab(PlanarArtifice.tabPA),
                new ItemBlock(BISMUTH_BLOCK).setRegistryName(BISMUTH_BLOCK.getRegistryName()).setTranslationKey(BISMUTH_BLOCK.getTranslationKey()).setCreativeTab(PlanarArtifice.tabPA),
                new ItemBlock(ALKIMIC_CONSTRUCT).setRegistryName(ALKIMIC_CONSTRUCT.getRegistryName()).setTranslationKey(ALKIMIC_CONSTRUCT.getTranslationKey()).setCreativeTab(PlanarArtifice.tabPA),
                new ItemBlock(ALKIMIUM_SMELTERY).setRegistryName(ALKIMIUM_SMELTERY.getRegistryName()).setTranslationKey(ALKIMIUM_SMELTERY.getTranslationKey()).setCreativeTab(PlanarArtifice.tabPA)
        );
    }

    @SuppressWarnings("ConstantConditions")
    public static void registerBlockModels(ModelRegistryEvent event) {
        registerItemModel(ALKIMIUM_BLOCK);
        registerItemModel(BISMUTH_BLOCK);
        registerItemModel(ALKIMIC_CONSTRUCT);
        registerItemModel(ALKIMIUM_SMELTERY);
    }

    @SuppressWarnings("ConstantConditions")
    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {
        ModelResourceLocation loc = new ModelResourceLocation(block.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, loc);
    }
}
