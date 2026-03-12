package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.block.BlockFlawlessMirror;
import mod.emt.planarartifice.block.BlockMaterialPA;
import mod.emt.planarartifice.block.BlockSmelterPA;
import mod.emt.planarartifice.client.renderers.tile.TileFlawlessMirrorTESR;
import mod.emt.planarartifice.item.blocks.ItemBlockFlawlessMirror;
import mod.emt.planarartifice.item.blocks.ItemBlockPA;
import mod.emt.planarartifice.tile.TileFlawlessMirror;
import mod.emt.planarartifice.tile.TileFlawlessMirrorEssentia;
import mod.emt.planarartifice.tile.TileSmelterPA;
import mod.emt.planarartifice.utils.helper.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
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
    public static BlockSmelterPA ALKIMIUM_SMELTERY_THAUMIUM;
    public static BlockSmelterPA ALKIMIUM_SMELTERY_VOID;
    public static BlockSmelterPA ALKIMIUM_SMELTERY_MITHRILLIUM;
    public static BlockSmelterPA ALKIMIUM_SMELTERY_ADAMINITE;
    public static BlockSmelterPA ALKIMIUM_SMELTERY_MITHMINITE;
    public static BlockMaterialPA BISMUTH_BLOCK;
    public static BlockFlawlessMirror FLAWLESS_MIRROR;
    public static BlockFlawlessMirror FLAWLESS_MIRROR_ESSENTIA;

    public static void registerBlocks(@Nonnull final RegistryEvent.Register<Block> event) {
        LogHelper.info("Registering blocks...");

        final IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(ALKIMIUM_BLOCK = new BlockMaterialPA("alkimium_block", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, true));
        registry.register(BISMUTH_BLOCK = new BlockMaterialPA("bismuth_block", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, true));
        registry.register(ALKIMIC_CONSTRUCT = new BlockMaterialPA("alkimic_construct", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, false));
        registry.register(ALKIMIUM_SMELTERY = new BlockSmelterPA("alkimium_smeltery", 10, 0.85F, 375));
        registry.register(ALKIMIUM_SMELTERY_THAUMIUM = new BlockSmelterPA("alkimium_smeltery_thaumium", 5, 0.90f, 375));
        registry.register(ALKIMIUM_SMELTERY_VOID = new BlockSmelterPA("alkimium_smeltery_void", 10, 0.95f, 375));

        //TODO: Register these blocks at this point if Thaumic Additions is loaded so they are grouped with the other smelters
//        registry.register(ALKIMIUM_SMELTERY_MITHRILLIUM = new BlockSmelterPA("alkimium_smeltery_mithrillium", 15, 1.0f, 1000));
//        registry.register(ALKIMIUM_SMELTERY_ADAMINITE = new BlockSmelterPA("alkimium_smeltery_adaminite", 10, 1.25f, 2000));
//        registry.register(ALKIMIUM_SMELTERY_MITHMINITE = new BlockSmelterPA("alkimium_smeltery_mithminite", 3, 1.5f, 4000));

        registry.register(FLAWLESS_MIRROR = new BlockFlawlessMirror(TileFlawlessMirror.class, "flawless_mirror"));
        registry.register(FLAWLESS_MIRROR_ESSENTIA = new BlockFlawlessMirror(TileFlawlessMirrorEssentia.class, "flawless_mirror_essentia"));



        registerTileEntities();
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileSmelterPA.class, new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_smeltery"));
        GameRegistry.registerTileEntity(TileFlawlessMirror.class, new ResourceLocation(PlanarArtifice.MOD_ID, "flawless_mirror"));
        GameRegistry.registerTileEntity(TileFlawlessMirrorEssentia.class, new ResourceLocation(PlanarArtifice.MOD_ID, "flawless_mirror_essentia"));
    }

    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(new ItemBlockPA(ALKIMIUM_BLOCK));
        registry.register(new ItemBlockPA(BISMUTH_BLOCK));
        registry.register(new ItemBlockPA(ALKIMIC_CONSTRUCT));
        registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY, EnumRarity.RARE));
        registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY_THAUMIUM, EnumRarity.RARE));
        registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY_VOID, EnumRarity.RARE));

        //TODO: Register these items at this point if Thaumic Additions is loaded so they are grouped with the other smelters
//        registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY_MITHRILLIUM, EnumRarity.RARE));
//        registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY_ADAMINITE, EnumRarity.RARE));
//        registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY_MITHMINITE, EnumRarity.RARE));


        registry.register(new ItemBlockFlawlessMirror(FLAWLESS_MIRROR));
        registry.register(new ItemBlockFlawlessMirror(FLAWLESS_MIRROR_ESSENTIA));
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public static void registerBlockModels(ModelRegistryEvent event) {
        registerItemModel(ALKIMIUM_BLOCK);
        registerItemModel(BISMUTH_BLOCK);
        registerItemModel(ALKIMIC_CONSTRUCT);
        registerItemModel(ALKIMIUM_SMELTERY);
        registerItemModel(ALKIMIUM_SMELTERY_THAUMIUM);
        registerItemModel(ALKIMIUM_SMELTERY_VOID);
        registerItemModel(ALKIMIUM_SMELTERY_ADAMINITE);
        registerItemModel(ALKIMIUM_SMELTERY_MITHMINITE);
        registerItemModel(ALKIMIUM_SMELTERY_MITHRILLIUM);
        registerItemModel(FLAWLESS_MIRROR);
        registerItemModel(FLAWLESS_MIRROR_ESSENTIA);

        ClientRegistry.bindTileEntitySpecialRenderer(TileFlawlessMirror.class, new TileFlawlessMirrorTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileFlawlessMirrorEssentia.class, new TileFlawlessMirrorTESR());
    }

    @SuppressWarnings("ConstantConditions")
    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {
        if(block != null) {
            ModelResourceLocation loc = new ModelResourceLocation(block.getRegistryName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, loc);
        }
    }
}
