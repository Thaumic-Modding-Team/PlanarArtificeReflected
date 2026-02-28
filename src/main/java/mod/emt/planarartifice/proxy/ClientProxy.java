package mod.emt.planarartifice.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    public void init() {
        super.init();
    }
    
    @Override
    public void postInit() {
        super.postInit();
    }

    public static EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }
}
