package mod.emt.planarartifice.tile;

import com.invadermonky.thaumicapi.api.tile.AbstractTileEssentiaSmelter;
import mod.emt.planarartifice.block.essentia.BlockSmelterPA;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileSmelterPA extends AbstractTileEssentiaSmelter {
    @Override
    public int getMaxEssentiaCapacity() {
        Block block = this.world.getBlockState(this.pos).getBlock();
        return block instanceof BlockSmelterPA ? ((BlockSmelterPA) block).getEssentiaCapacity() : 256;
    }

    @Override
    public int getTransferSpeed() {
        Block block = this.world.getBlockState(this.pos).getBlock();
        return block instanceof BlockSmelterPA ? ((BlockSmelterPA) block).getTransferSpeed() : 20;
    }

    @Override
    public float getBaseEfficiency() {
        Block block = this.world.getBlockState(this.pos).getBlock();
        return block instanceof BlockSmelterPA ? ((BlockSmelterPA) block).getEfficiency() : 0.8f;
    }

    @Override
    public int getBaseFluxProduced(int itemEssentia, int producedEssentia) {
        int fluxProduced = 0;
        float efficiency = this.getBaseEfficiency() - 0.05f;
        if(efficiency < 1.0f) {
            fluxProduced = (int) (itemEssentia * efficiency);
            if(fluxProduced % 2 == 1 && this.world.rand.nextFloat() <= efficiency) {
                fluxProduced++;
            }
        }
        return fluxProduced;
    }

    @Override
    public EnumFacing getMachineFront() {
        return this.world.getBlockState(this.pos).getValue(BlockSmelterPA.FACING);
    }

    @Override
    public void setBlockEnabledState(boolean isActive) {
        TileEntity tile = this.world.getTileEntity(this.pos);
        IBlockState state = this.world.getBlockState(this.pos);
        if(state.getValue(BlockSmelterPA.ENABLED) != isActive) {
            this.world.setBlockState(this.pos, state.withProperty(BlockSmelterPA.ENABLED, isActive));
            if(tile != null) {
                tile.validate();
                this.world.setTileEntity(this.pos, tile);
            }
        }
    }
}
