package mod.emt.planarartifice.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.ItemsTC;

import org.jetbrains.annotations.NotNull;

public class CreativeTabsPA extends CreativeTabs {
    public CreativeTabsPA(int length, String name) {
        super(length, name);
    }

    @SuppressWarnings("ConstantConditions")
    @SideOnly(Side.CLIENT)
    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ItemsTC.casterBasic);
    }
}
