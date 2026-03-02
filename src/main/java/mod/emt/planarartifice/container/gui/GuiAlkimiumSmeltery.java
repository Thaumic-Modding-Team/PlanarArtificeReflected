package mod.emt.planarartifice.container.gui;

import mod.emt.planarartifice.tile.PATileAlkimiumSmeltery;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import thaumcraft.Thaumcraft;
import thaumcraft.common.container.ContainerSmelter;

public class GuiAlkimiumSmeltery extends GuiContainer {
    ResourceLocation tex = new ResourceLocation(Thaumcraft.MODID, "textures/gui/gui_smelter.png");

    PATileAlkimiumSmeltery furnaceInventory;

    public GuiAlkimiumSmeltery(InventoryPlayer par1InventoryPlayer, PATileAlkimiumSmeltery par2TileEntityFurnace){
        super(new ContainerSmelter(par1InventoryPlayer, par2TileEntityFurnace));
        this.furnaceInventory = par2TileEntityFurnace;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        this.mc.renderEngine.bindTexture(this.tex);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        GlStateManager.enableBlend();
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        if(this.furnaceInventory.getBurnTimeRemainingScaled(20) > 0){
            int i = this.furnaceInventory.getBurnTimeRemainingScaled(20);
            this.drawTexturedModalRect(k + 80, l + 26 + 20 - i, 176, 20 - i, 16, i);
        }
        int i = this.furnaceInventory.getCookProgressScaled(46);
        this.drawTexturedModalRect(k + 106, l + 13 + 46 - i, 216, 46 - i, 9, i);
        i = this.furnaceInventory.getVisScaled(48);
        this.drawTexturedModalRect(k + 61, l + 12 + 48 - i, 200, 48 - i, 8, i);
        this.drawTexturedModalRect(k + 60, l + 8, 232, 0, 10, 55);

        GlStateManager.popMatrix();
    }
}
