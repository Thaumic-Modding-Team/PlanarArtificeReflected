package mod.emt.planarartifice.client.gui;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.utils.helper.PlayerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.client.lib.events.HudHandler;
import thaumcraft.common.tiles.devices.TileCondenser;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiHudHandlerPA extends Gui {
    private static final ResourceLocation texture = new ResourceLocation(PlanarArtifice.MOD_ID, "textures/gui/hud_aura_meter.png");
    private static final DecimalFormat secondsFormatter = new DecimalFormat("#######.#");
    private static GuiHudHandlerPA instance;

    public static GuiHudHandlerPA getInstance() {
        return instance == null ? instance = new GuiHudHandlerPA() : instance;
    }

    public void renderAuraMeterHud(float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
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
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.translate(2.0, 0, 0);

        if (vis > 0.0F) {
            GlStateManager.pushMatrix();
            Color c = new Color(Color.HSBtoRGB(count / 360, 0.75F, 1F));
            GlStateManager.color(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F, 1.0F);
            GlStateManager.translate(5.0D, start, 0.0D);
            GlStateManager.scale(1.0D, vis, 1.0D);
            this.drawTexturedModalRect(0.0F, 0.0F, 16, 8, 8, 64);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
            GlStateManager.translate(5.0D, start, 0.0D);
            this.drawTexturedModalRect(0, 0, 24, (int) (8 + count % 64), 8, (int) vis * 64);
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();

            if (player.isSneaking()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(16.0D, start, 0.0D);
                GlStateManager.scale(0.5D, 0.5D, 0.5D);
                String msg = secondsFormatter.format(HudHandler.currentAura.getVis());
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
            this.drawTexturedModalRect(0, 0, 16, 8, 8, 64);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GlStateManager.color(0.7F, 0.4F, 1.0F, 0.5F);
            GlStateManager.translate(5.0D, start, 0.0D);
            this.drawTexturedModalRect(0, 0, 104, (int) (120 - count2 % 64), 8, (int) flux * 64);
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();

            if (player.isSneaking()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(16.0D, (start - 4.0F), 0.0D);
                GlStateManager.scale(0.5D, 0.5D, 0.5D);
                String msg = secondsFormatter.format(HudHandler.currentAura.getFlux());
                mc.ingameGUI.drawString(mc.fontRenderer, msg, 0, 0, 11145659);
                GlStateManager.popMatrix();
                mc.renderEngine.bindTexture(texture);
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        this.drawTexturedModalRect(1, 1, 0, 0, 16, 80);
        GlStateManager.popMatrix();

        start = 8.0F + (1.0F - base) * 64.0F;
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        this.drawTexturedModalRect(1F, start, 0, 80, 16, 5);
        GlStateManager.popMatrix();

        GlStateManager.popMatrix();

    }

    public void renderGoggleHud(float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        if(player != null && player.isSneaking()) {
            RayTraceResult trace = player.rayTrace(PlayerHelper.getReachDistance(player), partialTicks);
            if(trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                TileEntity tile = player.world.getTileEntity(trace.getBlockPos());
                if(tile instanceof IEssentiaTransport) {
                    List<String> displayStrings = new ArrayList<>();
                    IEssentiaTransport transport = (IEssentiaTransport) tile;
                    EnumFacing face = trace.subHit >= 0 && trace.subHit < 6 ? EnumFacing.VALUES[trace.subHit] : trace.sideHit;

                    if(transport.getEssentiaType(face) != null) {
                        displayStrings.add(I18n.format("tc.resonator1", TextFormatting.RESET + "" + transport.getEssentiaAmount(face), transport.getEssentiaType(face).getName()));
                    } else if(tile instanceof IAspectContainer && ((IAspectContainer) tile).getAspects().size() > 0) {
                        IAspectContainer container = (IAspectContainer) tile;
                        for(Aspect aspect : container.getAspects().getAspectsSortedByName()) {
                            displayStrings.add(I18n.format(I18n.format("tc.resonator1", TextFormatting.RESET + "" + container.getAspects().getAmount(aspect), aspect.getName())));
                        }
                    }

                    String suctionType = I18n.format("tc.resonator3");
                    if(transport.getSuctionType(face) != null) {
                        suctionType = transport.getSuctionType(face).getName();
                    }
                    displayStrings.add(I18n.format("tc.resonator2", TextFormatting.RESET + "" + transport.getSuctionAmount(face), suctionType));

                    if(tile instanceof TileCondenser) {
                        TileCondenser condenser = (TileCondenser) tile;
                        displayStrings.add(I18n.format("tc.condenser1", TextFormatting.RESET + "" + condenser.cost));
                        displayStrings.add(I18n.format("tc.condenser2", TextFormatting.RESET + "" + condenser.interval, condenser.interval / 20));
                    }

                    ScaledResolution res = new ScaledResolution(mc);
                    int x = res.getScaledWidth() / 2;
                    int y = res.getScaledHeight() / 2 + 28;
                    this.drawTextBox(mc, displayStrings, x, y);
                    for (int i = 0, displayStringsSize = displayStrings.size(); i < displayStringsSize; i++) {
                        String str = displayStrings.get(i);
                        int offset = mc.fontRenderer.getStringWidth(str) / 2;
                        mc.fontRenderer.drawString(str, x - offset, y + i * 9, 0xffffff, true);
                    }


                }
            }
        }
    }

    private void drawTextBox(Minecraft mc, List<String> stringList, int startX, int startY) {
        int padding = 4;
        int maxHeight = 8 + (9 * (stringList.size() - 1));
        int maxWidth = 0;
        for(String str : stringList) {
            int width = mc.fontRenderer.getStringWidth(str);
            if(width > maxWidth) {
                maxWidth = width;
            }
        }
        drawRect(startX - (maxWidth / 2) - padding, startY - padding, startX + (maxWidth / 2) + padding, startY + maxHeight + padding, 0x66000000);
    }
}
