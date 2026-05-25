package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.config.ConfigHandlerPA;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;

import java.awt.*;

//Optional, these are only enabled manually by modpack developers
public class ModAspectsPA {
    public static Aspect DYE;
    public static Aspect SPACE;
    public static Aspect TIME;

    public static void registerSpatio() {
        SPACE = new Aspect("spatio", 0x4AF755, new Aspect[]{Aspect.VOID, Aspect.ENTROPY}, new ResourceLocation(PlanarArtifice.MOD_ID, "textures/aspects/spatio.png"), 1);
    }

    public static void registerTempus() {
        TIME = new Aspect("tempus", 0xD6DB43, new Aspect[]{ConfigHandlerPA.aspects.spatioAspect ? SPACE : Aspect.AURA, Aspect.EXCHANGE}, new ResourceLocation(PlanarArtifice.MOD_ID, "textures/aspects/tempus.png"), 1);
    }

    public static void registerTinctura() {
        DYE = new Aspect("tinctura", 0xFFFFFF, new Aspect[]{Aspect.EXCHANGE, Aspect.SENSES}, new ResourceLocation(PlanarArtifice.MOD_ID, "textures/aspects/tinctura.png"), 771) {
            @Override
            public int getColor() {
                float hue = (System.currentTimeMillis() % 4000) / 4000.0F;
                return Color.HSBtoRGB(hue, 1.0F, 1.0F);
            }
        };
    }
}
