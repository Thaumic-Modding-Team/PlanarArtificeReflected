package mod.emt.planarartifice.compat;

import mod.emt.planarartifice.compat.twu.IntegrationTWU;
import net.minecraftforge.fml.common.Loader;

public class CompatHandlerPA {
    public static boolean isThaumicAdditionsLoaded = Loader.isModLoaded("thaumadditions");
    public static boolean isThaumicWondersLoaded = Loader.isModLoaded("thaumicwonders");

    public static void init() {
        if (isThaumicWondersLoaded) {
            IntegrationTWU.init();
        }
    }
}
