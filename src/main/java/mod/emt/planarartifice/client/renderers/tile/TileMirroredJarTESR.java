package mod.emt.planarartifice.client.renderers.tile;

import mod.emt.planarartifice.tile.TileMirroredJar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.client.lib.RenderCubes;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.models.block.ModelJar;
import thaumcraft.common.config.ModConfig;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class TileMirroredJarTESR extends TileEntitySpecialRenderer<TileMirroredJar> {
    private ModelJar model = new ModelJar();
    private static final ResourceLocation TEX_LABEL = new ResourceLocation("thaumcraft", "textures/models/label.png");
    private static final ResourceLocation TEX_BRINE = new ResourceLocation("thaumcraft", "textures/models/jarbrine.png");
    private static final ItemStack MIRROR_ITEM = new ItemStack(BlocksTC.mirrorEssentia);
    private static final ItemStack MIRROR_ITEM_LINKED = new ItemStack(BlocksTC.mirrorEssentia, 1, 1);

    @Override
    public void render(@NotNull TileMirroredJar tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(tile, x, y, z, partialTicks, destroyStage, alpha);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        this.renderLidBrace(tile);
        this.renderLidExtension(tile);
        this.renderLabel(tile);
        this.renderMirrorItem(tile);
        this.renderEssentia(tile);
        GlStateManager.popMatrix();
    }

    private void renderLidBrace(TileMirroredJar tile) {
        if(tile.blocked) {
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.translate(0.5, 0.01, 0.5);
            GlStateManager.rotate(180f, 1.0f, 0, 0);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.scale(1.001, 1.001, 1.001);
            this.bindTexture(TEX_BRINE);
            this.model.renderLidBrace();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    private void renderLidExtension(TileMirroredJar tile) {
        if(ThaumcraftApiHelper.getConnectableTile(tile.getWorld(), tile.getPos(), EnumFacing.UP) != null) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.translate(0.5, 0.01, 0.5);
            GlStateManager.rotate(180f, 1.0f, 0, 0);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.scale(0.9, 1.0, 0.9);
            this.bindTexture(TEX_BRINE);
            this.model.renderLidExtension();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    private void renderLabel(TileMirroredJar tile) {
        if (tile.aspectFilter != null) {
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.disableLighting();
            GlStateManager.translate(0.5, 0.01, 0.5);
            GlStateManager.rotate(180f, 1.0f, 0, 0);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            switch (tile.facing) {
                case 3:
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 4:
                    GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 5:
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            }

            float rot = (float) ((tile.aspectFilter.getTag().hashCode() + tile.getPos().getX() + tile.facing) % 4 - 2);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, -0.5F, 0.315F);
            GlStateManager.scale(0.75, 0.75, 0.75);
            if (ModConfig.CONFIG_GRAPHICS.crooked) {
                GlStateManager.rotate(rot, 0.0F, 0.0F, 1.0F);
            }

            UtilsFX.renderQuadCentered(TEX_LABEL, 0.5F, 1.0F, 1.0F, 1.0F, -99, 771, 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, -0.5F, 0.316F);
            GlStateManager.scale(0.75, 0.75, 0.75);
            if (ModConfig.CONFIG_GRAPHICS.crooked) {
                GlStateManager.rotate(rot, 0.0F, 0.0F, 1.0F);
            }

            GlStateManager.scale(0.021, 0.021, 0.021);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            UtilsFX.drawTag(-8, -8, tile.aspectFilter);
            GlStateManager.popMatrix();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }

    }

    private void renderMirrorItem(TileMirroredJar tile) {
        if(tile.aspect == null && tile.amount <= 0) {
            ItemStack stack = tile.isLinked() ? MIRROR_ITEM_LINKED : MIRROR_ITEM;
            RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.translate(0.5, 0.02, 0.5);
            GlStateManager.rotate(90.0f, 1.0f, 0, 0);
            GlStateManager.scale(0.6, 0.6, 0.6);
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    private void renderEssentia(TileMirroredJar tile) {
        if(tile.aspect != null && tile.amount > 0) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.translate(0.5, 0.01, 0.5);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            RenderCubes renderBlocks = new RenderCubes();
            GlStateManager.disableLighting();
            float level = (float) tile.amount / 250.0F * 0.625F;
            Tessellator tessellator = Tessellator.getInstance();
            renderBlocks.setRenderBounds(0.25F, 0.0625F, 0.25F, 0.75F, (double) 0.0625F + (double) level, 0.75F);
            tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
            Color co = new Color(0);
            if (tile.aspect != null) {
                co = new Color(tile.aspect.getColor());
            }

            TextureAtlasSprite icon = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("thaumcraft:blocks/animatedglow");
            this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            renderBlocks.renderFaceYNeg(BlocksTC.jarNormal, -0.5F, 0.0F, -0.5F, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
            renderBlocks.renderFaceYPos(BlocksTC.jarNormal, -0.5F, 0.0F, -0.5F, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
            renderBlocks.renderFaceZNeg(BlocksTC.jarNormal, -0.5F, 0.0F, -0.5F, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
            renderBlocks.renderFaceZPos(BlocksTC.jarNormal, -0.5F, 0.0F, -0.5F, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
            renderBlocks.renderFaceXNeg(BlocksTC.jarNormal, -0.5F, 0.0F, -0.5F, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
            renderBlocks.renderFaceXPos(BlocksTC.jarNormal, -0.5F, 0.0F, -0.5F, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
            tessellator.draw();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
