package mod.emt.planarartifice.item.misc;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;
import thaumcraft.common.items.casters.ItemFocus;

import java.util.Objects;

public class ItemFocusPA extends ItemFocus {
    public ItemFocusPA(String name, int complexity) {
        super(name, complexity);
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
