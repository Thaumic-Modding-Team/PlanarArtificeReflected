package mod.emt.planarartifice.item.blocks;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ItemBlockPA extends ItemBlock {
    protected IRarity rarity;

    public ItemBlockPA(Block block, IRarity rarity) {
        super(block);
        this.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        this.setTranslationKey(block.getTranslationKey());
        this.setCreativeTab(PlanarArtifice.tabPA);
        this.rarity = rarity;
    }

    public ItemBlockPA(Block block) {
        this(block, null);
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return this.rarity != null ? this.rarity : super.getForgeRarity(stack);
    }
}
