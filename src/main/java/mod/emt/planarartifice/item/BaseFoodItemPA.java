package mod.emt.planarartifice.item;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.registry.ModItemsPA;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class BaseFoodItemPA extends ItemFood {
    public int itemUseDuration;
    public boolean alwaysEdible;

    public BaseFoodItemPA(String name, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setRegistryName(PlanarArtifice.MOD_ID, name);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
    }

    public BaseFoodItemPA(String name, int amount, float saturation, boolean isWolfFood, int eatingSpeed) {
        super(amount, saturation, isWolfFood);
        this.setRegistryName(PlanarArtifice.MOD_ID, name);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
        itemUseDuration = eatingSpeed; // 32 by default
    }

    @Override
    public int getMaxItemUseDuration(@NotNull ItemStack stack) {
        if (itemUseDuration == 0) {
            return 32;
        }

        return itemUseDuration;
    }

    @Override
    protected void onFoodEaten(@NotNull ItemStack stack, World worldIn, @NotNull EntityPlayer player) {
        if (!worldIn.isRemote) {
            if (this == ModItemsPA.THAUMATURGES_FRUIT) {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 10 * 20, 1));
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 240 * 20, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 240 * 20, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 120 * 20, 1));
            }
        }

        super.onFoodEaten(stack, worldIn, player);
    }

    public @NotNull BaseFoodItemPA setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }
}
