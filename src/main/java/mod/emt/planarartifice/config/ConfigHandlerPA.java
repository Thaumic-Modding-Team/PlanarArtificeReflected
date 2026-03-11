package mod.emt.planarartifice.config;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = PlanarArtifice.MOD_ID)
public class ConfigHandlerPA {
    @Config.Name("Flawless Magic Mirror")
    public static MirrorCategory flawlessMirror = new MirrorCategory();
    @Config.Name("Flawless Essentia Mirror")
    public static MirrorCategory flawlessMirrorEssentia = new MirrorCategory();

    public static class MirrorCategory {
        @Config.RangeInt(min = 0, max = 600)
        @Config.Name("Stabilization Ticks")
        @Config.Comment({
                "The number of ticks required for the flawless mirror to lose 1 instability. Default Thaumcraft",
                "mirrors use a value of 100."
        })
        public int stabilizesTicks = 20;

        @Config.RangeInt(min = 0, max = 10000)
        @Config.Name("Instability Threshold")
        @Config.Comment({
                "The amount of instability required for this mirror to produce 1 flux. This value is equal to the",
                "number of items or amount of essentia ejected from the mirror. Default Thaumcraft mirrors use values",
                "of 128 (items) and 64 (essentia)."
        })
        public int instabilityThreshold = 512;
    }

    @Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID)
    public static class ConfigChangeListener {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(PlanarArtifice.MOD_ID)) {
                ConfigManager.sync(PlanarArtifice.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
