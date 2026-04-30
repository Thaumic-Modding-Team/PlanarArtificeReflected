package mod.emt.planarartifice.network.packets;

import io.netty.buffer.ByteBuf;
import mod.emt.planarartifice.utils.helper.WorldHelper;
import mod.emt.planarartifice.utils.world.WorldSaveDataPA;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncWorldSaveDataClient implements IMessage {
    private NBTTagCompound saveData;

    public MessageSyncWorldSaveDataClient() {
        NBTTagCompound tag = new NBTTagCompound();
        WorldHelper.getWorldSaveDataPA().writeToNBT(tag);
        this.saveData = tag;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.saveData = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.saveData);
    }

    public static class MessageHandler implements IMessageHandler<MessageSyncWorldSaveDataClient, IMessage> {
        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(MessageSyncWorldSaveDataClient message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                WorldSaveDataPA saveDataPA = WorldHelper.getWorldSaveDataPA();
                saveDataPA.readFromNBT(message.saveData);
            });
            return null;
        }
    }
}
