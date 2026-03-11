package mod.emt.planarartifice.item;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;

public class BaseItemPA extends Item {
    private final EnumRarity rarity;

    public BaseItemPA(String name, EnumRarity rarity) {
        this.setRegistryName(PlanarArtifice.MOD_ID, name);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
        this.rarity = rarity;
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return rarity;
    }
}
