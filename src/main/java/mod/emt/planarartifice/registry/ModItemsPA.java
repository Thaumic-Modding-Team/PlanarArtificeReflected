package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.item.BaseFoodItemPA;
import mod.emt.planarartifice.item.BaseItemPA;
import mod.emt.planarartifice.item.bauble.PAItemAuraMeter;
import mod.emt.planarartifice.item.bauble.PAItemMirroredHeadband;
import mod.emt.planarartifice.utils.helper.LogHelper;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID)
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

    @SubscribeEvent
    public static void registerItems(@Nonnull final RegistryEvent.Register<Item> event) {
        LogHelper.info("Registering items...");

        final IForgeRegistry<Item> registry = event.getRegistry();

        registry.registerAll(
                ModRegistryPA.setup(new BaseItemPA(EnumRarity.EPIC), "planar_orb"),
                ModRegistryPA.setup(new BaseItemPA(EnumRarity.RARE), "alkimium_ingot"),
                ModRegistryPA.setup(new BaseItemPA(EnumRarity.RARE), "alkimium_nugget"),
                ModRegistryPA.setup(new BaseItemPA(EnumRarity.RARE), "alkimium_plate"),
                ModRegistryPA.setup(new BaseItemPA(EnumRarity.RARE), "bismuth_ingot"),
                ModRegistryPA.setup(new BaseItemPA(EnumRarity.RARE), "bismuth_nugget"),
                ModRegistryPA.setup(new BaseItemPA(EnumRarity.RARE), "bismuth_plate"),
                ModRegistryPA.setup(new PAItemAuraMeter(), "aura_meter"),
                ModRegistryPA.setup(new PAItemMirroredHeadband(), "mirromirous_headband"),
                ModRegistryPA.setup(new BaseFoodItemPA(6, 1.4F, false), "thaumaturges_fruit")

        );

        // Item Blocks
        ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> block.getRegistryName().getNamespace().equals(PlanarArtifice.MOD_ID))
                .filter(block -> !(block instanceof BlockDoor)) // Doors should not have an item block registered
                .filter(block -> !(block instanceof BlockSlab)) // Slabs should not have an item block registered
                .forEach(block -> registry.register(ModRegistryPA.setup(new ItemBlock(block), block.getRegistryName())));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRegisterModelsEvent(@Nonnull final ModelRegistryEvent event) {
        // Item Models
        for (final Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item.getRegistryName().getNamespace().equals(PlanarArtifice.MOD_ID)) {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
    }
}
