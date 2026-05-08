package mod.emt.planarartifice.utils.helper;

import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class PlayerHelper {
    public static double getReachDistance(EntityPlayer player) {
        IAttributeInstance instance = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE);
        return instance != null ? instance.getAttributeValue() : EntityPlayer.REACH_DISTANCE.getDefaultValue();
    }

    public static UUID getUUIDFromPlayer(EntityPlayer player) {
        return player.getGameProfile().getId();
    }
}
