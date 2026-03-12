package mod.emt.planarartifice.item.bauble;

import baubles.api.BaubleType;
import baubles.api.render.IRenderBauble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

public class ItemMirroredBelt extends AbstractBaublePA implements IRenderBauble {
    public ItemMirroredBelt(String name, IRarity rarity) {
        super(name, rarity);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.BELT;
    }

    @Override
    public void onPlayerBaubleRender(ItemStack itemStack, EntityPlayer entityPlayer, RenderType renderType, float v) {

    }
}
