package mod.emt.planarartifice.utils.helper;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;

public class WorldHelper {
    /**
     * Gets the World object from the dimension id.
     *
     * @param dimensionId The dimension id.
     * @param forceLoadWorld Attempts to initialize the world object if it is missing
     * @return The world object or null if it is not found
     */
    @Nullable
    public static World getWorldFromId(int dimensionId, boolean forceLoadWorld) {
        World target = DimensionManager.getWorld(dimensionId);
        if(target == null && forceLoadWorld) {
            DimensionManager.initDimension(dimensionId);
            target = DimensionManager.getWorld(dimensionId);
        }
        return target;
    }

    /**
     * Returns the tile entity at the given position in the passed dimension.
     *
     * @param dimensionId The dimension id
     * @param pos The tile entity position
     * @param forceLoadWorld Attempts to initialize the world object if it is missing
     * @return The tile entity if it is found, null otherwise
     */
    @Nullable
    public static TileEntity getWorldTileEntity(int dimensionId, BlockPos pos, boolean forceLoadWorld) {
        World world = getWorldFromId(dimensionId, forceLoadWorld);
        if(world != null) {
            return world.getTileEntity(pos);
        }
        return null;
    }
}
