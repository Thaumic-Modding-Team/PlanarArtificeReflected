package mod.emt.planarartifice.client.renderers.tile;

import mod.emt.planarartifice.block.BlockStarvingChest;
import mod.emt.planarartifice.tile.TileStarvingChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

public class TileStarvingChestTESR extends TileEntitySpecialRenderer<TileStarvingChest> {
    private ModelChest chestModel = new ModelChest();

    @Override
    public void render(TileStarvingChest chest, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing facing = EnumFacing.NORTH;
        if (chest.hasWorld()) {
            IBlockState state = chest.getWorld().getBlockState(chest.getPos());
            if (state.getPropertyKeys().contains(BlockStarvingChest.FACING)) facing = state.getValue(BlockStarvingChest.FACING);
        }

        ModelChest chestModel = this.chestModel;
        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            this.bindTexture(chest.getTextureLocation());
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        if (destroyStage < 0) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }

        GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        short rotate = 0;
        if (facing == EnumFacing.NORTH) {
            rotate = 180;
        } else if (facing == EnumFacing.WEST) {
            rotate = 90;
        } else if (facing == EnumFacing.EAST) {
            rotate = -90;
        }

        GlStateManager.rotate(rotate, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        float rotateLid = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * partialTicks;
        rotateLid = 1.0F - rotateLid;
        rotateLid = 1.0F - rotateLid * rotateLid * rotateLid;
        chestModel.chestLid.rotateAngleX = -(rotateLid * 3.1415927F / 2.0F);
        chestModel.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
