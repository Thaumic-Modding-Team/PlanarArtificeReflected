package mod.emt.planarartifice.compat;

import mod.emt.planarartifice.compat.twu.TWUIntegration;
import net.minecraftforge.fml.common.Loader;

public class PACompatHandler {
    public static void init() {
        if (Loader.isModLoaded("thaumicwonders")) {
            TWUIntegration.init();
        }
    }
}
