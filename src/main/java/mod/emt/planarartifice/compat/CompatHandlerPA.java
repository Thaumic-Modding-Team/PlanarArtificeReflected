package mod.emt.planarartifice.compat;

import mod.emt.planarartifice.compat.twu.IntegrationTWU;
import net.minecraftforge.fml.common.Loader;

public class CompatHandlerPA {
    public static void init() {
        if (Loader.isModLoaded("thaumicwonders")) {
            IntegrationTWU.init();
        }
    }
}
