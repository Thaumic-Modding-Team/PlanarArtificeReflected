package mod.emt.planarartifice.event;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.client.gui.GuiAuraMeterHudPA;
import mod.emt.planarartifice.item.bauble.PAItemAuraMeter;
import mod.emt.planarartifice.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = PlanarArtifice.MOD_ID, value = Side.CLIENT)
public class HudHandlerEvent {
    @SubscribeEvent
    public static void onRenderLast(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE && PAItemAuraMeter.shouldRenderHud(ClientProxy.getClientPlayer())) {
            new GuiAuraMeterHudPA(Minecraft.getMinecraft(), event.getPartialTicks(), ClientProxy.getClientPlayer());
        }
    }
}
