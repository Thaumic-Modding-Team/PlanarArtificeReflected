package mod.emt.planarartifice.proxy;

import mod.emt.planarartifice.item.misc.ItemFocusPA;
import mod.emt.planarartifice.item.tools.ItemCasterPA;
import mod.emt.planarartifice.registry.ModItemsPA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    public void init() {
        super.init();
        ItemCasterPA.initClient(ModItemsPA.BISMUTH_CASTER);
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        itemColors.registerItemColorHandler((stack, tintIndex) -> ((ItemFocusPA) stack.getItem()).getFocusColor(stack), ModItemsPA.FLAWLESS_FOCUS);
    }
    
    @Override
    public void postInit() {
        super.postInit();
    }

    public static EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }
}
