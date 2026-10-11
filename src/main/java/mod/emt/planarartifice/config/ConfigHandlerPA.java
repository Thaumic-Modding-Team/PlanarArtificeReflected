package mod.emt.planarartifice.config;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = PlanarArtifice.MOD_ID)
public class ConfigHandlerPA {
    @Config.Name("Aspects")
    public static AspectCategory aspects = new AspectCategory();
    @Config.Name("Flawless Magic Mirror")
    public static MirrorCategory flawlessMirror = new MirrorCategory();
    @Config.Name("Flawless Essentia Mirror")
    public static MirrorCategory flawlessMirrorEssentia = new MirrorCategory();

    public static class AspectCategory {
        @Config.Name("Aspect: Spatio")
        @Config.Comment("Enables the 'Spatio' aspect. CraftTweaker or GroovyScript is required to utilize it in recipes or other sources.")
        public boolean spatioAspect = false;

        @Config.Name("Aspect: Tempus")
        @Config.Comment("Enables the 'Tempus' aspect. CraftTweaker or GroovyScript is required to utilize it in recipes or other sources.")
        public boolean tempusAspect = false;

        @Config.Name("Aspect: Tinctura")
        @Config.Comment("Enables the 'Tinctura' aspect. CraftTweaker or GroovyScript is required to utilize it in recipes or other sources.")
        public boolean tincturaAspect = false;
    }

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
                "of 128 (items) and 64 (essentia). Setting this value to 0 will disable flux pollution."
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
