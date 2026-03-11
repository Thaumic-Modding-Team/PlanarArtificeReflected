package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.block.BlockFlawlessMirror;
import mod.emt.planarartifice.block.BlockMaterialPA;
import mod.emt.planarartifice.block.BlockSmelterPA;
import mod.emt.planarartifice.client.renderers.tile.TileFlawlessMirrorTESR;
import mod.emt.planarartifice.item.ItemBlockFlawlessMirror;
import mod.emt.planarartifice.tile.TileFlawlessMirror;
import mod.emt.planarartifice.tile.TileFlawlessMirrorEssentia;
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
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

public class ModBlocksPA {
    public static BlockMaterialPA ALKIMIC_CONSTRUCT;
    public static BlockMaterialPA ALKIMIUM_BLOCK;
    public static BlockSmelterPA ALKIMIUM_SMELTERY;
    public static BlockMaterialPA BISMUTH_BLOCK;
    public static BlockFlawlessMirror FLAWLESS_MIRROR;
    public static BlockFlawlessMirror FLAWLESS_MIRROR_ESSENTIA;

    public static void registerBlocks(@Nonnull final RegistryEvent.Register<Block> event) {
        LogHelper.info("Registering blocks...");

        final IForgeRegistry<Block> registry = event.getRegistry();

        registry.registerAll(
                ALKIMIUM_BLOCK = new BlockMaterialPA("alkimium_block", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, true),
                BISMUTH_BLOCK = new BlockMaterialPA("bismuth_block", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, true),
                ALKIMIC_CONSTRUCT = new BlockMaterialPA("alkimic_construct", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, false),
                ALKIMIUM_SMELTERY = new BlockSmelterPA("alkimium_smeltery", 14, 0.85F, 375),
                FLAWLESS_MIRROR = new BlockFlawlessMirror(TileFlawlessMirror.class, "flawless_mirror"),
                FLAWLESS_MIRROR_ESSENTIA = new BlockFlawlessMirror(TileFlawlessMirrorEssentia.class, "flawless_mirror_essentia")
        );

        registerTileEntities();
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileSmelterPA.class, new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_smeltery"));
        GameRegistry.registerTileEntity(TileFlawlessMirror.class, new ResourceLocation(PlanarArtifice.MOD_ID, "flawless_mirror"));
        GameRegistry.registerTileEntity(TileFlawlessMirrorEssentia.class, new ResourceLocation(PlanarArtifice.MOD_ID, "flawless_mirror_essentia"));
    }

    @SuppressWarnings("ConstantConditions")
    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.registerAll(
                new ItemBlock(ALKIMIUM_BLOCK).setRegistryName(ALKIMIUM_BLOCK.getRegistryName()).setTranslationKey(ALKIMIUM_BLOCK.getTranslationKey()).setCreativeTab(PlanarArtifice.tabPA),
                new ItemBlock(BISMUTH_BLOCK).setRegistryName(BISMUTH_BLOCK.getRegistryName()).setTranslationKey(BISMUTH_BLOCK.getTranslationKey()).setCreativeTab(PlanarArtifice.tabPA),
                new ItemBlock(ALKIMIC_CONSTRUCT).setRegistryName(ALKIMIC_CONSTRUCT.getRegistryName()).setTranslationKey(ALKIMIC_CONSTRUCT.getTranslationKey()).setCreativeTab(PlanarArtifice.tabPA),
                new ItemBlock(ALKIMIUM_SMELTERY).setRegistryName(ALKIMIUM_SMELTERY.getRegistryName()).setTranslationKey(ALKIMIUM_SMELTERY.getTranslationKey()).setCreativeTab(PlanarArtifice.tabPA),
                new ItemBlockFlawlessMirror(FLAWLESS_MIRROR),
                new ItemBlockFlawlessMirror(FLAWLESS_MIRROR_ESSENTIA)
        );
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public static void registerBlockModels(ModelRegistryEvent event) {
        registerItemModel(ALKIMIUM_BLOCK);
        registerItemModel(BISMUTH_BLOCK);
        registerItemModel(ALKIMIC_CONSTRUCT);
        registerItemModel(ALKIMIUM_SMELTERY);
        registerItemModel(FLAWLESS_MIRROR);
        registerItemModel(FLAWLESS_MIRROR_ESSENTIA);

        ClientRegistry.bindTileEntitySpecialRenderer(TileFlawlessMirror.class, new TileFlawlessMirrorTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileFlawlessMirrorEssentia.class, new TileFlawlessMirrorTESR());
    }

    @SuppressWarnings("ConstantConditions")
    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {
        ModelResourceLocation loc = new ModelResourceLocation(block.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, loc);
    }
}
