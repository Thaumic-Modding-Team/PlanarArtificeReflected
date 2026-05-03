package mod.emt.planarartifice.item.tools;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.registry.ModEnchantsPA;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.util.EnumHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

public class ItemBismuthClaymore extends ItemSword {
    public static ItemTool.ToolMaterial BISMUTH_MATERIAL = EnumHelper.addToolMaterial("BISMUTH", 4, 2081, 10.0F, 2.5F, 25);

    public ItemBismuthClaymore(String name) {
        super(BISMUTH_MATERIAL);
        this.setRegistryName(PlanarArtifice.MOD_ID, name);
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
    }

    @Override
    public void getSubItems(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack(this);
            EnumInfusionEnchantment.addInfusionEnchantment(stack, ModEnchantsPA.METAPHIZE, 2);
            items.add(stack);
        }
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.RARE;
    }
}
