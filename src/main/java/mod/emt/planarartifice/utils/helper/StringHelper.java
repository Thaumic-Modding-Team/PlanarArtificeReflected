package mod.emt.planarartifice.utils.helper;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import org.apache.commons.lang3.text.WordUtils;

public class StringHelper {
    public static String getDimensionName(int dimensionId) {
        if (!DimensionManager.isDimensionRegistered(dimensionId)) {
            return Integer.toString(dimensionId);
        }
        DimensionType type = DimensionManager.getProviderType(dimensionId);
        if (type == null) {
            return Integer.toString(dimensionId);
        }
        String name = type.getName();
        int[] dims = DimensionManager.getDimensions(type);
        if (dims != null && dims.length > 1) {
            name += " " + dimensionId;
        }
        return WordUtils.capitalizeFully(name.replace("_", " "));
    }
}
