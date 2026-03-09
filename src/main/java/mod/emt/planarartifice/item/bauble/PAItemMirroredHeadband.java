package mod.emt.planarartifice.item.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.item.BaseItemPA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IVisDiscountGear;
import thaumcraft.client.lib.UtilsFX;

public class PAItemMirroredHeadband extends BaseItemPA implements IBauble, IRenderBauble, IVisDiscountGear {
    ResourceLocation texture = new ResourceLocation(PlanarArtifice.MOD_ID, "textures/items/mirromirous_headband_worn.png");

    public PAItemMirroredHeadband() {
        super(EnumRarity.EPIC);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.canRepair = false;
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.HEAD;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float ticks) {
        if (type == RenderType.HEAD) {
            boolean armor = !player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty();
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
            Helper.translateToHeadLevel(player);
            Helper.translateToFace();
            Helper.defaultTransforms();
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5D, -0.5D, armor ? 0.11999999731779099D : 0.0D);
            UtilsFX.renderTextureIn3D(0.0F, 0.0F, 1.0F, 1.0F, 16, 26, 0.1F);
        }
    }

    @Override
    public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer) {
        return 3;
    }
}
