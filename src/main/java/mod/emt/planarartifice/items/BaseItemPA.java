package mod.emt.planarartifice.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;

public class BaseItemPA extends Item {
    private final EnumRarity rarity;

    public BaseItemPA(EnumRarity rarity) {
        super();
        this.rarity = rarity;
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return rarity;
    }
}
