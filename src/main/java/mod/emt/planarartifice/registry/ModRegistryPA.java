package mod.emt.planarartifice.registry;

import java.lang.reflect.Field;
import java.util.HashMap;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thaumcraft.common.lib.events.PlayerEvents;

@Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID)
public class ModRegistryPA {
    private static Field lastChargeField;

    @Nonnull
    public static <T extends IForgeRegistryEntry> T setup(@Nonnull final T entry, @Nonnull final String name) {
        return setup(entry, new ResourceLocation(PlanarArtifice.MOD_ID, name));
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName) {
        Preconditions.checkNotNull(entry, "Entry to setup must not be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign must not be null!");

        entry.setRegistryName(registryName);
        if (entry instanceof Block)
            ((Block) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath()).setCreativeTab(PlanarArtifice.tabPA);
        if (entry instanceof Item)
            ((Item) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath()).setCreativeTab(PlanarArtifice.tabPA);
        return entry;
    }

    // Get Runic Shielding amount
    public static int getRunicShielding(EntityPlayer player) {
        try {
            if (lastChargeField == null)
                lastChargeField = PlayerEvents.class.getDeclaredField("lastCharge");
            if (!lastChargeField.isAccessible())
                lastChargeField.setAccessible(true);

            HashMap<Integer, Integer> lastCharge = (HashMap<Integer, Integer>) lastChargeField.get(null);
            return lastCharge.getOrDefault(player.getEntityId(), 0);
        } catch (Exception ignored) {
            return 0;
        }
    }

    @SubscribeEvent
    public static void registerRecipes(@Nonnull final RegistryEvent.Register<IRecipe> event) {
        OreDictionary.registerOre("blockAlkimium", ModBlocksPA.ALKIMIUM_BLOCK);
        OreDictionary.registerOre("blockBismuth", ModBlocksPA.BISMUTH_BLOCK);

        OreDictionary.registerOre("ingotAlkimium", ModItemsPA.ALKIMIUM_INGOT);
        OreDictionary.registerOre("nuggetAlkimium", ModItemsPA.ALKIMIUM_NUGGET);
        OreDictionary.registerOre("plateAlkimium", ModItemsPA.ALKIMIUM_PLATE);
        OreDictionary.registerOre("ingotBismuth", ModItemsPA.BISMUTH_INGOT);
        OreDictionary.registerOre("nuggetBismuth", ModItemsPA.BISMUTH_NUGGET);
        OreDictionary.registerOre("plateBismuth", ModItemsPA.BISMUTH_PLATE);
    }
}
