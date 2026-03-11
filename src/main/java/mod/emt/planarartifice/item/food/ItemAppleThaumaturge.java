package mod.emt.planarartifice.item.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;

public class ItemAppleThaumaturge extends ItemFoodPA {
    private final boolean isEnchanted;

    public ItemAppleThaumaturge(String name, int amount, float saturation, boolean isEnchanted) {
        super(name, amount, saturation, false);
        this.setAlwaysEdible();
        this.isEnchanted = isEnchanted;
    }

    @Override
    protected void onFoodEaten(@NotNull ItemStack stack, World worldIn, @NotNull EntityPlayer player) {
        if(!worldIn.isRemote) {
            if(this.isEnchanted) {
                //Adds ~20% duration compared to normal Enchanted Golden Apple
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 2));
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 7200, 1));
                player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 7200, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 3));
            } else {
                //Grants 1 minute of Enchanted Golden Apple effects
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 1));
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1200, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1200, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 1));
            }
        }
    }

    @Override
    public boolean hasEffect(@NotNull ItemStack stack) {
        return this.isEnchanted;
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return !this.isEnchanted ? EnumRarity.RARE : EnumRarity.EPIC;
    }
}
