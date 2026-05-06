package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.block.BlockFlawlessMirror;
import mod.emt.planarartifice.block.BlockMaterialPA;
import mod.emt.planarartifice.block.BlockStarvingChest;
import mod.emt.planarartifice.block.essentia.*;
import mod.emt.planarartifice.block.glass.BlockGlassPA;
import mod.emt.planarartifice.block.glass.BlockGlassPassable;
import mod.emt.planarartifice.block.glass.BlockGlassRedstone;
import mod.emt.planarartifice.client.renderers.tile.TileCentrifugeTESR;
import mod.emt.planarartifice.client.renderers.tile.TileFlawlessMirrorTESR;
import mod.emt.planarartifice.client.renderers.tile.TileMirroredJarTESR;
import mod.emt.planarartifice.client.renderers.tile.TileStarvingChestTESR;
import mod.emt.planarartifice.compat.CompatHandlerPA;
import mod.emt.planarartifice.item.blocks.ItemBlockFlawlessMirror;
import mod.emt.planarartifice.item.blocks.ItemBlockMirroredJar;
import mod.emt.planarartifice.item.blocks.ItemBlockPA;
import mod.emt.planarartifice.tile.*;
import mod.emt.planarartifice.utils.helper.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
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
    public static Block ALKIMIC_CONSTRUCT;
    public static Block ALKIMIUM_BLOCK;
    public static Block ALKIMIUM_CENTRIFUGE;
    public static Block ALKIMIUM_SMELTER_VENT;
    public static Block ALKIMIUM_SMELTER_AUX;
    public static Block ALKIMIUM_SMELTERY;
    public static Block ALKIMIUM_SMELTERY_THAUMIUM;
    public static Block ALKIMIUM_SMELTERY_VOID;
    public static Block BISMUTH_BLOCK;
    public static Block FLAWLESS_MIRROR;
    public static Block FLAWLESS_MIRROR_ESSENTIA;
    public static Block GLASS_CLEAR;
    public static Block GLASS_CRYSTAL;
    public static Block GLASS_DARK;
    public static Block GLASS_ENTITY;
    public static Block GLASS_ETHEREAL;
    public static Block GLASS_HARDENED;
    public static Block GLASS_LIGHT;
    public static Block GLASS_NON_LIVING;
    public static Block GLASS_REDSTONE;
    public static Block MIRRORED_JAR;
    public static Block STARVING_CHEST_SMALL;
    public static Block STARVING_CHEST_MEDIUM;
    public static Block STARVING_CHEST_LARGE;
    public static Block STARVING_CHEST_HUGE;

    //Thaumic Additions
    public static BlockSmelterPA ALKIMIUM_SMELTERY_ADAMINITE;
    public static BlockSmelterPA ALKIMIUM_SMELTERY_MITHMINITE;
    public static BlockSmelterPA ALKIMIUM_SMELTERY_MITHRILLIUM;

    public static void registerBlocks(@Nonnull final RegistryEvent.Register<Block> event) {
        LogHelper.info("Registering blocks...");

        final IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(GLASS_CLEAR = new BlockGlassPA("glass_clear"));
        registry.register(GLASS_CRYSTAL = new BlockGlassPA("glass_crystal").setRenderLayer(BlockRenderLayer.TRANSLUCENT));
        registry.register(GLASS_HARDENED = new BlockGlassPA("glass_hardened").setHardness(3.0f).setResistance(2000.0f));

        registry.register(GLASS_LIGHT = new BlockGlassPA("glass_light").setLightLevel(1.0f));
        registry.register(GLASS_DARK = new BlockGlassPA("glass_dark").setLightOpacity(15));
        registry.register(GLASS_REDSTONE = new BlockGlassRedstone("glass_redstone"));

        registry.register(GLASS_ENTITY = new BlockGlassPA("glass_entity").setCollisionPredicate(entity -> entity instanceof EntityLivingBase));
        registry.register(GLASS_NON_LIVING = new BlockGlassPA("glass_non_living").setCollisionPredicate(entity -> !(entity instanceof EntityLivingBase)));
        registry.register(GLASS_ETHEREAL = new BlockGlassPassable("glass_ethereal"));

        registry.register(ALKIMIUM_BLOCK = new BlockMaterialPA("alkimium_block", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, true));
        registry.register(BISMUTH_BLOCK = new BlockMaterialPA("bismuth_block", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, true));
        registry.register(ALKIMIC_CONSTRUCT = new BlockMaterialPA("alkimic_construct", Material.IRON, MapColor.LIME, 5.0F, 15.0F, SoundType.METAL, false));
        registry.register(STARVING_CHEST_SMALL = new BlockStarvingChest("starving_chest_small", 1));
        registry.register(STARVING_CHEST_MEDIUM = new BlockStarvingChest("starving_chest_medium", 2));
        registry.register(STARVING_CHEST_LARGE = new BlockStarvingChest("starving_chest_large", 3));
        registry.register(STARVING_CHEST_HUGE = new BlockStarvingChest("starving_chest_huge", 4));
        registry.register(ALKIMIUM_SMELTERY = new BlockSmelterPA("alkimium_smeltery", 10, 0.85F, 375));
        registry.register(ALKIMIUM_SMELTERY_THAUMIUM = new BlockSmelterPA("alkimium_smeltery_thaumium", 5, 0.95f, 375));
        registry.register(ALKIMIUM_SMELTERY_VOID = new BlockSmelterPA("alkimium_smeltery_void", 10, 1.0f, 375));

        if(CompatHandlerPA.isThaumicAdditionsLoaded) {
            registry.register(ALKIMIUM_SMELTERY_MITHRILLIUM = new BlockSmelterPA("alkimium_smeltery_mithrillium", 15, 1.0f, 1000));
            registry.register(ALKIMIUM_SMELTERY_ADAMINITE = new BlockSmelterPA("alkimium_smeltery_adaminite", 10, 1.25f, 2000));
            registry.register(ALKIMIUM_SMELTERY_MITHMINITE = new BlockSmelterPA("alkimium_smeltery_mithminite", 3, 1.5f, 4000));
        }

        registry.register(ALKIMIUM_CENTRIFUGE = new BlockCentrifugePA("alkimium_centrifuge"));
        registry.register(ALKIMIUM_SMELTER_AUX = new BlockSmelterAuxiliaryPA("alkimium_smelter_aux", 2));
        registry.register(ALKIMIUM_SMELTER_VENT = new BlockSmelterVentPA("alkimium_smelter_vent", 0.5f));
        registry.register(FLAWLESS_MIRROR = new BlockFlawlessMirror(TileFlawlessMirror.class, "flawless_mirror"));
        registry.register(FLAWLESS_MIRROR_ESSENTIA = new BlockFlawlessMirror(TileFlawlessMirrorEssentia.class, "flawless_mirror_essentia"));
        registry.register(MIRRORED_JAR = new BlockMirroredJar());



        registerTileEntities();
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileCentrifiguePA.class, new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_centrifuge"));
        GameRegistry.registerTileEntity(TileFlawlessMirror.class, new ResourceLocation(PlanarArtifice.MOD_ID, "flawless_mirror"));
        GameRegistry.registerTileEntity(TileFlawlessMirrorEssentia.class, new ResourceLocation(PlanarArtifice.MOD_ID, "flawless_mirror_essentia"));
        GameRegistry.registerTileEntity(TilePassableGlass.class, new ResourceLocation(PlanarArtifice.MOD_ID, "glass_ethereal"));
        GameRegistry.registerTileEntity(TileMirroredJar.class, new ResourceLocation(PlanarArtifice.MOD_ID, "mirrored_jar"));
        GameRegistry.registerTileEntity(TileSmelterPA.class, new ResourceLocation(PlanarArtifice.MOD_ID, "alkimium_smeltery"));
        GameRegistry.registerTileEntity(TileStarvingChest.class, new ResourceLocation(PlanarArtifice.MOD_ID, "starving_chest"));
    }

    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(new ItemBlockPA(GLASS_CLEAR));
        registry.register(new ItemBlockPA(GLASS_CRYSTAL));
        registry.register(new ItemBlockPA(GLASS_HARDENED));

        registry.register(new ItemBlockPA(GLASS_LIGHT));
        registry.register(new ItemBlockPA(GLASS_DARK));
        registry.register(new ItemBlockPA(GLASS_REDSTONE));

        registry.register(new ItemBlockPA(GLASS_ENTITY, EnumRarity.RARE));
        registry.register(new ItemBlockPA(GLASS_NON_LIVING, EnumRarity.RARE));
        registry.register(new ItemBlockPA(GLASS_ETHEREAL, EnumRarity.RARE));

        registry.register(new ItemBlockPA(ALKIMIUM_BLOCK, EnumRarity.RARE));
        registry.register(new ItemBlockPA(BISMUTH_BLOCK, EnumRarity.RARE));
        registry.register(new ItemBlockPA(ALKIMIC_CONSTRUCT, EnumRarity.RARE));
        registry.register(new ItemBlockPA(STARVING_CHEST_SMALL));
        registry.register(new ItemBlockPA(STARVING_CHEST_MEDIUM));
        registry.register(new ItemBlockPA(STARVING_CHEST_LARGE));
        registry.register(new ItemBlockPA(STARVING_CHEST_HUGE));
        registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY, EnumRarity.RARE));
        registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY_THAUMIUM, EnumRarity.RARE));
        registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY_VOID, EnumRarity.RARE));

        if(CompatHandlerPA.isThaumicAdditionsLoaded) {
            registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY_MITHRILLIUM, EnumRarity.RARE));
            registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY_ADAMINITE, EnumRarity.RARE));
            registry.register(new ItemBlockPA(ALKIMIUM_SMELTERY_MITHMINITE, EnumRarity.RARE));
        }

        registry.register(new ItemBlockPA(ALKIMIUM_SMELTER_AUX, EnumRarity.RARE));
        registry.register(new ItemBlockPA(ALKIMIUM_SMELTER_VENT, EnumRarity.RARE));
        registry.register(new ItemBlockPA(ALKIMIUM_CENTRIFUGE, EnumRarity.RARE));
        registry.register(new ItemBlockFlawlessMirror(FLAWLESS_MIRROR));
        registry.register(new ItemBlockFlawlessMirror(FLAWLESS_MIRROR_ESSENTIA));
        registry.register(new ItemBlockMirroredJar(MIRRORED_JAR));
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    @SideOnly(Side.CLIENT)
    public static void registerBlockModels(ModelRegistryEvent event) {
        registerItemModel(ALKIMIUM_BLOCK);
        registerItemModel(BISMUTH_BLOCK);
        registerItemModel(ALKIMIC_CONSTRUCT);
        registerItemModel(ALKIMIUM_CENTRIFUGE);
        registerItemModel(ALKIMIUM_SMELTER_AUX);
        registerItemModel(ALKIMIUM_SMELTER_VENT);
        registerItemModel(ALKIMIUM_SMELTERY);
        registerItemModel(ALKIMIUM_SMELTERY_THAUMIUM);
        registerItemModel(ALKIMIUM_SMELTERY_VOID);
        registerItemModel(ALKIMIUM_SMELTERY_ADAMINITE);
        registerItemModel(ALKIMIUM_SMELTERY_MITHMINITE);
        registerItemModel(ALKIMIUM_SMELTERY_MITHRILLIUM);
        registerItemModel(FLAWLESS_MIRROR);
        registerItemModel(FLAWLESS_MIRROR_ESSENTIA);
        registerItemModel(GLASS_CLEAR);
        registerItemModel(GLASS_CRYSTAL);
        registerItemModel(GLASS_DARK);
        registerItemModel(GLASS_ENTITY);
        registerItemModel(GLASS_ETHEREAL);
        registerItemModel(GLASS_HARDENED);
        registerItemModel(GLASS_LIGHT);
        registerItemModel(GLASS_NON_LIVING);
        registerItemModel(GLASS_REDSTONE);
        registerItemModel(MIRRORED_JAR);
        registerItemModel(STARVING_CHEST_HUGE);
        registerItemModel(STARVING_CHEST_LARGE);
        registerItemModel(STARVING_CHEST_MEDIUM);
        registerItemModel(STARVING_CHEST_SMALL);

        ClientRegistry.bindTileEntitySpecialRenderer(TileCentrifiguePA.class, new TileCentrifugeTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileFlawlessMirror.class, new TileFlawlessMirrorTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileFlawlessMirrorEssentia.class, new TileFlawlessMirrorTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMirroredJar.class, new TileMirroredJarTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileStarvingChest.class, new TileStarvingChestTESR());
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
