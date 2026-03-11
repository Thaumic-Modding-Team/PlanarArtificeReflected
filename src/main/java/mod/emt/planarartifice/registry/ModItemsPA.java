package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.item.BaseFoodItemPA;
import mod.emt.planarartifice.item.BaseItemPA;
import mod.emt.planarartifice.item.bauble.PAItemAuraMeter;
import mod.emt.planarartifice.item.bauble.PAItemMirroredHeadband;
import mod.emt.planarartifice.utils.helper.LogHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@GameRegistry.ObjectHolder(PlanarArtifice.MOD_ID)
public class ModItemsPA {
    public static final BaseItemPA ALKIMIUM_INGOT = null;
    public static final BaseItemPA ALKIMIUM_NUGGET = null;
    public static final BaseItemPA ALKIMIUM_PLATE = null;
    public static final PAItemAuraMeter AURA_METER = null;
    public static final BaseItemPA BISMUTH_INGOT = null;
    public static final BaseItemPA BISMUTH_NUGGET = null;
    public static final BaseItemPA BISMUTH_PLATE = null;
    public static final PAItemMirroredHeadband MIRROMIROUS_HEADBAND = null;
    public static final BaseItemPA PLANAR_ORB = null;
    public static final BaseFoodItemPA THAUMATURGES_FRUIT = null;

    public static void registerItems(@Nonnull final RegistryEvent.Register<Item> event) {
        LogHelper.info("Registering items...");

        final IForgeRegistry<Item> registry = event.getRegistry();

        registry.registerAll(
                new BaseItemPA("planar_orb", EnumRarity.EPIC),
                new BaseItemPA("alkimium_ingot", EnumRarity.RARE),
                new BaseItemPA("alkimium_nugget", EnumRarity.RARE),
                new BaseItemPA("alkimium_plate", EnumRarity.RARE),
                new BaseItemPA("bismuth_ingot", EnumRarity.RARE),
                new BaseItemPA("bismuth_nugget", EnumRarity.RARE),
                new BaseItemPA("bismuth_plate", EnumRarity.RARE),
                new PAItemAuraMeter(),
                new PAItemMirroredHeadband(),
                new BaseFoodItemPA("thaumaturges_fruit", 6, 1.4F, false)

        );
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
        registerItemModel(PLANAR_ORB);
        registerItemModel(THAUMATURGES_FRUIT);
    }

    @SuppressWarnings("ConstantConditions")
    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Item item) {
        ModelResourceLocation loc = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, loc);
    }
}
