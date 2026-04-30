package mod.emt.planarartifice.network;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.network.packets.MessageSyncWorldSaveDataClient;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(PlanarArtifice.MOD_ID);

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(MessageSyncWorldSaveDataClient.MessageHandler.class, MessageSyncWorldSaveDataClient.class, id++, Side.CLIENT);
    }
}
