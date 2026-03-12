package mod.emt.planarartifice.item.tools;

import com.invadermonky.thaumicapi.api.item.AbstractItemCaster;
import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ItemCasterPA extends AbstractItemCaster {
    protected int drainRadius;
    protected float consumptionModifier;

    public ItemCasterPA(String name, int augmentSlots, int drainRadius, float consumptionModifier) {
        super(augmentSlots);
        this.setRegistryName(PlanarArtifice.MOD_ID, name);
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
        this.drainRadius = drainRadius;
        this.consumptionModifier = consumptionModifier;
    }

    @Override
    public int getChunkDrainRadius(EntityPlayer entityPlayer, ItemStack itemStack) {
        return this.drainRadius;
    }

    @Override
    public float getConsumptionModifier(ItemStack itemStack, EntityPlayer entityPlayer, boolean b) {
        return this.consumptionModifier;
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
