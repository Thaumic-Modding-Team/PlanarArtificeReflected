package mod.emt.planarartifice;

import mod.emt.planarartifice.proxy.CommonProxy;
import mod.emt.planarartifice.registry.CreativeTabsTH;
import mod.emt.planarartifice.utils.helpers.LogHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = PlanarArtifice.MOD_ID,
        name = PlanarArtifice.MOD_NAME,
        version = PlanarArtifice.MOD_VERSION,
        dependencies = PlanarArtifice.DEPENDENCIES
)
public class ThaumicHorizons {
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_NAME = "Cursed Artifice";
    public static final String MOD_VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "required-after:thaumcraft";

    public static final String CLIENT_PROXY = "planarartifice.proxy.ClientProxy";
    public static final String COMMON_PROXY = "planarartifice.proxy.CommonProxy";

    public static final CreativeTabs tabPA = new CreativeTabsPA(CreativeTabs.CREATIVE_TAB_ARRAY.length, "PlanarArtificeTab");

    @Mod.Instance(MOD_ID)
    public static PlanarArtifice instance;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("Starting " + MOD_NAME);
        proxy.preInit();
        LogHelper.debug("Finished preInit phase.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        LogHelper.debug("Finished init phase.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        LogHelper.debug("Finished postInit phase.");
    }
}
