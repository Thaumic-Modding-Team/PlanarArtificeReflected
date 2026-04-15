package mod.emt.planarartifice.item.misc;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;
import thaumcraft.common.items.casters.ItemFocus;

import java.util.Objects;

public class ItemFocusPA extends ItemFocus {
    public ItemFocusPA(String name, int complexity) {
        super(name, complexity);
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
    }

    //This is necessary, otherwise it won't show up on the creative tab
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == PlanarArtifice.tabPA || tab == CreativeTabs.SEARCH) {
            items.add(new ItemStack(this, 1, 0));
        }
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
