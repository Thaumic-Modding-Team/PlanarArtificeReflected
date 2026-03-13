package mod.emt.planarartifice.item;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BaseItemPA extends Item {
    private final IRarity rarity;

    public BaseItemPA(String name, IRarity rarity) {
        this.setRegistryName(PlanarArtifice.MOD_ID, name);
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
        this.rarity = rarity;
    }

    public BaseItemPA(String name) {
        this(name, null);
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return this.rarity != null ? this.rarity : super.getForgeRarity(stack);
    }
}
