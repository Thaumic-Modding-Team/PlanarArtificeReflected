package mod.emt.planarartifice.client.renderers.tile;

import mod.emt.planarartifice.PlanarArtifice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import thaumcraft.Thaumcraft;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.tile.TileMirrorRenderer;
import thaumcraft.common.lib.utils.BlockStateUtils;
import thaumcraft.common.tiles.devices.TileMirror;
import thaumcraft.common.tiles.devices.TileMirrorEssentia;

public class TileFlawlessMirrorTESR extends TileMirrorRenderer {
    public static final ResourceLocation mirrorPane = new ResourceLocation(Thaumcraft.MODID, "textures/blocks/mirrorpane.png");
    public static final ResourceLocation mirrorPaneTrans = new ResourceLocation(Thaumcraft.MODID, "textures/blocks/mirrorpanetrans.png");
    public static final String flawlessMirrorFrame = PlanarArtifice.MOD_ID + ":blocks/flawless_mirror_frame";
    public static final String flawlessMirrorEssentiaFrame = PlanarArtifice.MOD_ID + ":blocks/flawless_mirror_essentia_frame";

    @Override
    public void render(TileEntity tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing dir = BlockStateUtils.getFacing(tile.getBlockMetadata());
        boolean linked = false;
        if (tile instanceof TileMirror) {
            linked = ((TileMirror)tile).linked;
        }

        if (tile instanceof TileMirrorEssentia) {
            linked = ((TileMirrorEssentia)tile).linked;
        }

        IBlockState state = tile.getWorld().getBlockState(tile.getPos());
        int lightmapCoords = state.getPackedLightmapCoords(tile.getWorld(), tile.getPos());
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.translateFromOrientationPA((float)x, (float)y, (float)z, dir.ordinal(), 0.01F);
        UtilsFX.renderItemIn2D(tile.getBlockType() == BlocksTC.mirror ? flawlessMirrorFrame : flawlessMirrorEssentiaFrame, 0.0625F);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        if (linked && FMLClientHandler.instance().getClient().player.getDistanceSqToCenter(tile.getPos()) < (double)1024.0F) {
            GlStateManager.pushMatrix();
            switch (dir) {
                case DOWN:
                    this.drawPlaneYPos(tile, x, y, z, partialTicks);
                    break;
                case UP:
                    this.drawPlaneYNeg(tile, x, y, z, partialTicks);
                    break;
                case WEST:
                    this.drawPlaneXPos(tile, x, y, z, partialTicks);
                    break;
                case EAST:
                    this.drawPlaneXNeg(tile, x, y, z, partialTicks);
                    break;
                case NORTH:
                    this.drawPlaneZPos(tile, x, y, z, partialTicks);
                    break;
                case SOUTH:
                    this.drawPlaneZNeg(tile, x, y, z, partialTicks);
            }

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.translateFromOrientationPA((float)x, (float)y, (float)z, dir.ordinal(), 0.02F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.5F, -0.5F, 0.0F);
            UtilsFX.renderQuadCentered(mirrorPaneTrans, 1.0F, 1.0F, 1.0F, 1.0F, lightmapCoords, 771, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        } else {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.translateFromOrientationPA((float)x, (float)y, (float)z, dir.ordinal(), 0.02F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.5F, -0.5F, 0.0F);
            UtilsFX.renderQuadCentered(mirrorPane, 1.0F, 1.0F, 1.0F, 1.0F, lightmapCoords, 771, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    private void translateFromOrientationPA(float x, float y, float z, int orientation, float off) {
        if (orientation == 0) {
            GlStateManager.translate(x, y + 1.0F, z + 1.0F);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        } else if (orientation == 1) {
            GlStateManager.translate(x, y, z);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        } else if (orientation == 2) {
            GlStateManager.translate(x, y, z + 1.0F);
        } else if (orientation == 3) {
            GlStateManager.translate(x + 1.0F, y, z);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        } else if (orientation == 4) {
            GlStateManager.translate(x + 1.0F, y, z + 1.0F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        } else if (orientation == 5) {
            GlStateManager.translate(x, y, z);
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
        }

        GlStateManager.translate(0.0F, 0.0F, -off);
    }
}
