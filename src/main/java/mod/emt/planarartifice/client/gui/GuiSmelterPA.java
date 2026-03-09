package mod.emt.planarartifice.client.gui;

import com.invadermonky.thaumicapi.api.tile.AbstractTileEssentiaSmelter;
import mod.emt.planarartifice.inventory.containers.ContainerSmelterPA;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSmelterPA extends GuiContainer {
    public static final ResourceLocation texture = new ResourceLocation("thaumcraft", "textures/gui/gui_smelter.png");

    public GuiSmelterPA(EntityPlayer player, AbstractTileEssentiaSmelter smelter) {
        super(new ContainerSmelterPA(player, smelter));
    }

    public ContainerSmelterPA getContainer() {
        return (ContainerSmelterPA) this.inventorySlots;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        GlStateManager.enableBlend();
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        this.renderBurnTime(k, l);
        this.renderProgress(k, l);
        this.renderEssentiaAmount(k, l);
        this.drawTexturedModalRect(k + 60, l + 8, 232, 0, 10, 55);
        GlStateManager.disableBlend();
    }

    protected void renderBurnTime(int k, int l) {
        int burnTimeMax = this.getContainer().smelter.burnTimeMax;
        int burnTimeScaled = this.getContainer().smelter.burnTime * 20 / (burnTimeMax == 0 ? 200 : burnTimeMax);
        if(burnTimeScaled > 0) {
            this.drawTexturedModalRect(k + 80, l + 26 + 20 - burnTimeScaled, 176, 20 - burnTimeScaled, 16, burnTimeScaled);
        }
    }

    protected void renderProgress(int k, int l) {
        int progressMax = this.getContainer().smelter.progressMax;
        int progressScaled = this.getContainer().smelter.progress * 46 / (progressMax == 0 ? 200 : progressMax);
        this.drawTexturedModalRect(k + 106, l + 13 + 46 - progressScaled, 216, 46 - progressScaled, 9, progressScaled);    }

    protected void renderEssentiaAmount(int k, int l) {
        int essentiaMax = this.getContainer().smelter.getMaxEssentiaCapacity();
        int essentiaScaled = this.getContainer().smelter.getCurrentEssentiaTotal() * 48 / (essentiaMax == 0 ? 200 : essentiaMax);
        this.drawTexturedModalRect(k + 61, l + 12 + 48 - essentiaScaled, 200, 48 - essentiaScaled, 8, essentiaScaled);
        this.drawTexturedModalRect(k + 60, l + 8, 232, 0, 10, 55);
    }
}
