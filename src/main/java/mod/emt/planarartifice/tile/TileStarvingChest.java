package mod.emt.planarartifice.tile;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.block.BlockStarvingChest;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import thaumcraft.api.ThaumcraftInvHelper;
import thaumcraft.common.tiles.devices.TileHungryChest;

public class TileStarvingChest extends TileHungryChest {

    @Override
    public void update() {
        super.update();
        this.pullSurroundingItems();
    }

    public void pullSurroundingItems() {
        IBlockState state = this.world.getBlockState(this.pos);
        if(state.getBlock() instanceof BlockStarvingChest) {
            int radius = ((BlockStarvingChest) state.getBlock()).getSuctionRange(this.world, this.pos, state);
            AxisAlignedBB area = new AxisAlignedBB(this.pos.add(-radius, 0, -radius), this.pos.add(radius + 1, radius + 1, radius + 1));
            for(EntityItem entityItem : this.world.getEntitiesWithinAABB(EntityItem.class, area)) {
                if(!validateEntity(entityItem)) continue;
                double x = (0.5 + pos.getX()) - entityItem.posX;
                double y = (0.5 + pos.getY()) - entityItem.posY;
                double z = (0.5 + pos.getZ()) - entityItem.posZ;
                double dist = Math.sqrt(x * x + y * y + z * z);
                if ((entityItem.motionX * x) < 0) entityItem.motionX = 0;
                if ((entityItem.motionY * y) < 0) entityItem.motionY = 0;
                if ((entityItem.motionZ * z) < 0) entityItem.motionZ = 0;
                entityItem.motionX += 0.05 * x / dist;
                entityItem.motionY += 0.05 * y / dist;
                entityItem.motionZ += 0.05 * z / dist;
            }
        }
    }

    public boolean validateEntity(EntityItem entityItem) {
        return entityItem.isEntityAlive() && !entityItem.getItem().isEmpty()
                && ThaumcraftInvHelper.hasRoomForSome(this.world, this.pos, EnumFacing.UP, entityItem.getItem());
    }

    public ResourceLocation getTextureLocation() {
        Block block = this.getBlockType();
        if(block instanceof BlockStarvingChest) {
            return ((BlockStarvingChest) block).getTextureLocation();
        }
        return new ResourceLocation(PlanarArtifice.MOD_ID, "textures/models/starving_chest_small.png");
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        if (!player.isSpectator() && this.getBlockType() instanceof BlockStarvingChest) {
            --this.numPlayersUsing;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), true);
            this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), true);
        }
    }
}
