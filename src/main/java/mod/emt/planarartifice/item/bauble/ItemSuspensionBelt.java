package mod.emt.planarartifice.item.bauble;

import baubles.api.BaubleType;
import baubles.api.render.IRenderBauble;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import thaumcraft.api.items.IVisDiscountGear;

public class ItemSuspensionBelt extends AbstractBaublePA implements IRenderBauble, IVisDiscountGear {
    public ItemSuspensionBelt() {
        super("suspension_belt", EnumRarity.EPIC);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            player.fallDistance = 0;
            if (player.world.isRemote) {
                boolean isJumpPressed = Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
                boolean isCrouchPressed = Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown();
                if (isCrouchPressed && player.motionY < 0) {
                    //Creative check to avoid interfering with creative flight.
                    if (!player.isCreative()) {
                        player.motionY = Math.max(player.motionY, -0.25);
                        player.velocityChanged = true;
                    }
                } else if (isJumpPressed && player.motionY < 0) {
                    player.motionY = 0;
                    player.velocityChanged = true;
                }

            }
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.BELT;
    }

    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType renderType, float v) {
        //TODO:
    }

    @Override
    public int getVisDiscount(ItemStack stack, EntityPlayer player) {
        return 5;
    }
}
