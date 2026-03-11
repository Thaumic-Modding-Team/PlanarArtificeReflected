package mod.emt.planarartifice.item.food;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ItemFoodPA extends ItemFood {
    public int itemUseDurationPA;

    public ItemFoodPA(String name, int amount, float saturation, boolean isWolfFood, int eatingSpeed) {
        super(amount, saturation, isWolfFood);
        this.setRegistryName(PlanarArtifice.MOD_ID, name);
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
        this.itemUseDurationPA = eatingSpeed; // 32 by default
    }

    public ItemFoodPA(String name, int amount, float saturation, boolean isWolfFood) {
        this(name, amount, saturation, isWolfFood, 32);
    }

    @Override
    public int getMaxItemUseDuration(@NotNull ItemStack stack) {
        if (this.itemUseDurationPA == 0) {
            return 32;
        }
        return this.itemUseDurationPA;
    }
}
