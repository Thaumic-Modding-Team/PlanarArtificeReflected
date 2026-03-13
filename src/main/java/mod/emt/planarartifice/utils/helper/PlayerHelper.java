package mod.emt.planarartifice.utils.helper;

import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerHelper {
    public static double getReachDistance(EntityPlayer player) {
        IAttributeInstance instance = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE);
        return instance != null ? instance.getAttributeValue() : EntityPlayer.REACH_DISTANCE.getDefaultValue();
    }
}
