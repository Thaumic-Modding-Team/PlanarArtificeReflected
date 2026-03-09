package mod.emt.planarartifice.client.gui;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.events.HudHandler;

import java.awt.*;
import java.text.DecimalFormat;

public class GuiAuraMeterHudPA extends Gui {
    private static final ResourceLocation texture = new ResourceLocation(PlanarArtifice.MOD_ID, "textures/gui/hud_aura_meter.png");
    DecimalFormat secondsFormatter = new DecimalFormat("#######.#");

    public GuiAuraMeterHudPA(Minecraft mc, float partialTicks, EntityPlayer player) {
        mc.getTextureManager().bindTexture(texture);

        float base = MathHelper.clamp(HudHandler.currentAura.getBase() / 525.0F, 0.0F, 1.0F);
        float vis = MathHelper.clamp(HudHandler.currentAura.getVis() / 525.0F, 0.0F, 1.0F);
        float flux = MathHelper.clamp(HudHandler.currentAura.getFlux() / 525.0F, 0.0F, 1.0F);
        assert Minecraft.getMinecraft().getRenderViewEntity() != null;
        float count = (Minecraft.getMinecraft().getRenderViewEntity()).ticksExisted + partialTicks;
        float count2 = (Minecraft.getMinecraft().getRenderViewEntity()).ticksExisted / 3.0F + partialTicks;

        if (flux + vis > 1.0F) {
            float m = 1.0F / (flux + vis);
            base *= m;
            vis *= m;
            flux *= m;
        }

        float start = 10.0F + (1.0F - vis) * 64.0F;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (vis > 0.0F) {
            GlStateManager.pushMatrix();
            Color c = new Color(Color.HSBtoRGB(count / 360, 0.75F, 1F));
            GlStateManager.color(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F, 1.0F);
            GlStateManager.translate(5.0D, start, 0.0D);
            GlStateManager.scale(1.0D, vis, 1.0D);
            drawTexturedModalRect(0.0F, 0.0F, 16, 8, 8, 64);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
            GlStateManager.translate(5.0D, start, 0.0D);
            drawTexturedModalRect(0, 0, 24, (int) (8 + count % 64), 8, (int) vis * 64);
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();

            if (player.isSneaking()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(16.0D, start, 0.0D);
                GlStateManager.scale(0.5D, 0.5D, 0.5D);
                String msg = this.secondsFormatter.format(HudHandler.currentAura.getVis());
                mc.ingameGUI.drawString(mc.fontRenderer, msg, 0, 0, c.getRGB());
                GlStateManager.popMatrix();
                mc.renderEngine.bindTexture(texture);
            }
        }

        if (flux > 0.0F) {
            start = 10.0F + (1.0F - flux - vis) * 64.0F;
            GlStateManager.pushMatrix();
            GlStateManager.color(0.25F, 0.1F, 0.3F, 1.0F);
            GlStateManager.translate(5.0D, start, 0.0D);
            GlStateManager.scale(1.0D, flux, 1.0D);
            drawTexturedModalRect(0, 0, 16, 8, 8, 64);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GlStateManager.color(0.7F, 0.4F, 1.0F, 0.5F);
            GlStateManager.translate(5.0D, start, 0.0D);
            drawTexturedModalRect(0, 0, 104, (int) (120 - count2 % 64), 8, (int) flux * 64);
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();

            if (player.isSneaking()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(16.0D, (start - 4.0F), 0.0D);
                GlStateManager.scale(0.5D, 0.5D, 0.5D);
                String msg = this.secondsFormatter.format(HudHandler.currentAura.getFlux());
                mc.ingameGUI.drawString(mc.fontRenderer, msg, 0, 0, 11145659);
                GlStateManager.popMatrix();
                mc.renderEngine.bindTexture(texture);
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        drawTexturedModalRect(1, 1, 0, 0, 16, 80);
        GlStateManager.popMatrix();

        start = 8.0F + (1.0F - base) * 64.0F;
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        drawTexturedModalRect(1F, start, 0, 80, 16, 5);
        GlStateManager.popMatrix();

        GlStateManager.popMatrix();

    }
}
