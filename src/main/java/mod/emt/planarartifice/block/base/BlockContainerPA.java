package mod.emt.planarartifice.block.base;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public abstract class BlockContainerPA extends BlockContainer {
    public BlockContainerPA(String name, Material materialIn) {
        super(materialIn);
        this.setRegistryName(PlanarArtifice.MOD_ID, name);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
    }

    public BlockContainerPA(String name, Material materialIn, MapColor color) {
        super(materialIn, color);
        this.setRegistryName(PlanarArtifice.MOD_ID, name);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
    }
}
