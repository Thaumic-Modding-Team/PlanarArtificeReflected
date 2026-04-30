package mod.emt.planarartifice.client.renderers.tile;

import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.tile.TileCentrifiguePA;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.models.block.ModelCentrifuge;

@SideOnly(Side.CLIENT)
public class TileCentrifugeTESR extends TileEntitySpecialRenderer<TileCentrifiguePA> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(PlanarArtifice.MOD_ID, "textures/models/alkimium_centrifuge.png");
    private final ModelCentrifuge model = new ModelCentrifuge();


    @Override
    public void render(@NotNull TileCentrifiguePA tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(tile, x, y, z, partialTicks, destroyStage, alpha);

        this.bindTexture(TEXTURE);
        GlStateManager.pushMatrix();
        if(destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0f, 4.0f, 1.0f);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        }

        GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);
        this.model.renderBoxes();
        GlStateManager.rotate(tile.rotation, 0, 1, 0);
        this.model.renderSpinnyBit();
        if(destroyStage >= 0) {
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        }
        GlStateManager.popMatrix();
    }
}
