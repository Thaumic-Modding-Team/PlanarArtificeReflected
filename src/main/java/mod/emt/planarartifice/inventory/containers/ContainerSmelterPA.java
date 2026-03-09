package mod.emt.planarartifice.inventory.containers;

import com.invadermonky.thaumicapi.api.tile.AbstractTileEssentiaSmelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

public class ContainerSmelterPA extends Container {
    public final AbstractTileEssentiaSmelter smelter;
    public int burnTime;
    public int burnTimeMax;
    public int progress;
    public int progressMax;
    public int essentiaAmount;
    public int essentiaAmountMax;

    public ContainerSmelterPA(EntityPlayer player, AbstractTileEssentiaSmelter smelter) {
        this.smelter = smelter;
        this.bindTileInventory();
        this.bindPlayerInventory(player);
    }

    protected void bindTileInventory() {
        //Fuel slot
        this.addSlotToContainer(new SlotItemHandler(this.smelter.handler, 0, 80, 8));
        //Item slot
        this.addSlotToContainer(new SlotItemHandler(this.smelter.handler, 1, 80, 48));
    }

    protected void bindPlayerInventory(EntityPlayer player) {
        InventoryPlayer inventory = player.inventory;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public @NotNull ItemStack transferStackInSlot(@NotNull EntityPlayer playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();
            if(index != 0 && index != 1) {
                AspectList aspectList = ThaumcraftCraftingManager.getObjectTags(slotStack);
                if(TileEntityFurnace.isItemFuel(slotStack)) {
                    if(!this.mergeItemStack(slotStack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(aspectList != null && aspectList.size() > 0) {
                    if(!this.mergeItemStack(slotStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(index < 29) {
                    if(!this.mergeItemStack(slotStack, 29, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.mergeItemStack(slotStack, 2, 29, false)) {
                    return ItemStack.EMPTY;
                }
            } else if(!this.mergeItemStack(slotStack, 2, 38, false)) {
                return ItemStack.EMPTY;
            }

            if(slotStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if(slotStack.getCount() != stack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, slotStack);
        }
        return stack;
    }

    @Override
    public boolean canInteractWith(@NotNull EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void addListener(@NotNull IContainerListener listener) {
        super.addListener(listener);
        listener.sendWindowProperty(this, 0, this.smelter.burnTime);
        listener.sendWindowProperty(this, 1, this.smelter.burnTimeMax);
        listener.sendWindowProperty(this, 2, this.smelter.progress);
        listener.sendWindowProperty(this, 3, this.smelter.progressMax);
        listener.sendWindowProperty(this, 4, this.smelter.getCurrentEssentiaTotal());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int smelterEssentia = this.smelter.getCurrentEssentiaTotal();
        int smelterEssentiaMax = this.smelter.getMaxEssentiaCapacity();
        for(IContainerListener listener : this.listeners) {
            if (this.burnTime != this.smelter.burnTime)
                listener.sendWindowProperty(this, 0, this.smelter.burnTime);
            if (this.burnTimeMax != this.smelter.burnTimeMax)
                listener.sendWindowProperty(this, 1, this.smelter.burnTimeMax);
            if (this.progress != this.smelter.progress)
                listener.sendWindowProperty(this, 2, this.smelter.progress);
            if (this.progressMax != this.smelter.progressMax)
                listener.sendWindowProperty(this, 3, this.smelter.progressMax);
            if (this.essentiaAmount != smelterEssentia)
                listener.sendWindowProperty(this, 4, smelterEssentia);
            if (this.essentiaAmountMax != smelterEssentiaMax)
                listener.sendWindowProperty(this, 5, smelterEssentiaMax);
        }

        this.burnTime = this.smelter.burnTime;
        this.burnTimeMax = this.smelter.burnTimeMax;
        this.progress = this.smelter.progress;
        this.progressMax = this.smelter.progressMax;
        this.essentiaAmount = smelterEssentia;
        this.essentiaAmountMax = smelterEssentiaMax;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        switch (id) {
            case 0:
                this.smelter.burnTime = data;
                break;
            case 1:
                this.smelter.burnTimeMax = data;
                break;
            case 2:
                this.smelter.progress = data;
                break;
            case 3:
                this.smelter.progressMax = data;
        }
    }
}
