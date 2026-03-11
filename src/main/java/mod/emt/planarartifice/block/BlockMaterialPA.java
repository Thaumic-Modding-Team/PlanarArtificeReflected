package mod.emt.planarartifice.block;

import mod.emt.planarartifice.block.base.BlockPA;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.NotNull;

public class BlockMaterialPA extends BlockPA {
    protected boolean beaconBlock;

    public BlockMaterialPA(String name, Material material, MapColor mapColor, float hardness, float resistance, SoundType soundType, boolean beaconBlock) {
        super(name, material, mapColor);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setSoundType(soundType);
        this.beaconBlock = beaconBlock;
    }

    public BlockMaterialPA(String name, Material material, MapColor mapColor, float hardness, SoundType soundType) {
        super(name, material, mapColor);
        this.setHardness(hardness);
        this.setSoundType(soundType);
    }

    @Override
    public boolean isBeaconBase(@NotNull IBlockAccess worldObj, @NotNull BlockPos pos, @NotNull BlockPos beacon) {
        return beaconBlock;
    }
}
