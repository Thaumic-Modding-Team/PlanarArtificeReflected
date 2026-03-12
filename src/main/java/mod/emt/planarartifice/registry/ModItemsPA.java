package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.item.BaseItemPA;
import mod.emt.planarartifice.item.bauble.ItemAuraMeter;
import mod.emt.planarartifice.item.bauble.ItemMirroredAmulet;
import mod.emt.planarartifice.item.bauble.ItemMirroredHeadband;
import mod.emt.planarartifice.item.food.ItemAppleThaumaturge;
import mod.emt.planarartifice.utils.helper.LogHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

public class ModItemsPA {
    public static BaseItemPA ALKIMIUM_INGOT;
    public static BaseItemPA ALKIMIUM_NUGGET;
    public static BaseItemPA ALKIMIUM_PLATE;
    public static ItemAuraMeter AURA_METER;
    public static BaseItemPA BISMUTH_INGOT;
    public static BaseItemPA BISMUTH_NUGGET;
    public static BaseItemPA BISMUTH_PLATE;
    public static ItemMirroredHeadband MIRROMIROUS_HEADBAND;
    public static ItemMirroredAmulet MIRRORED_AMULET;
    public static BaseItemPA PLANAR_ORB;
    public static ItemFood THAUMATURGES_FRUIT;
    public static ItemFood THAUMATURGES_FRUIT_ENCHANTED;

    public static void registerItems(@Nonnull final RegistryEvent.Register<Item> event) {
        LogHelper.info("Registering items...");

        final IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(PLANAR_ORB = new BaseItemPA("planar_orb", EnumRarity.EPIC));
        registry.register(ALKIMIUM_INGOT = new BaseItemPA("alkimium_ingot", EnumRarity.RARE));
        registry.register(ALKIMIUM_NUGGET = new BaseItemPA("alkimium_nugget", EnumRarity.RARE));
        registry.register(ALKIMIUM_PLATE = new BaseItemPA("alkimium_plate", EnumRarity.RARE));
        registry.register(BISMUTH_INGOT = new BaseItemPA("bismuth_ingot", EnumRarity.RARE));
        registry.register(BISMUTH_NUGGET = new BaseItemPA("bismuth_nugget", EnumRarity.RARE));
        registry.register(BISMUTH_PLATE = new BaseItemPA("bismuth_plate", EnumRarity.RARE));
        registry.register(AURA_METER = new ItemAuraMeter());
        registry.register(MIRROMIROUS_HEADBAND = new ItemMirroredHeadband());
        registry.register(MIRRORED_AMULET = new ItemMirroredAmulet());
        registry.register(THAUMATURGES_FRUIT = new ItemAppleThaumaturge("thaumaturges_fruit", 6, 1.4F, false));
        registry.register(THAUMATURGES_FRUIT_ENCHANTED = new ItemAppleThaumaturge("thaumaturges_fruit_enchanted", 6, 1.4F, true));
    }

    @SuppressWarnings("ConstantConditions")
    @SideOnly(Side.CLIENT)
    public static void registerItemModels(@Nonnull final ModelRegistryEvent event) {
        // Item Models
        registerItemModel(ALKIMIUM_INGOT);
        registerItemModel(ALKIMIUM_NUGGET);
        registerItemModel(ALKIMIUM_PLATE);
        registerItemModel(AURA_METER);
        registerItemModel(BISMUTH_INGOT);
        registerItemModel(BISMUTH_NUGGET);
        registerItemModel(BISMUTH_PLATE);
        registerItemModel(MIRROMIROUS_HEADBAND);
        registerItemModel(MIRRORED_AMULET);
        registerItemModel(PLANAR_ORB);
        registerItemModel(THAUMATURGES_FRUIT);
        registerItemModel(THAUMATURGES_FRUIT_ENCHANTED);
    }

    @SuppressWarnings("ConstantConditions")
    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Item item) {
        ModelResourceLocation loc = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, loc);
    }
}
