package mod.emt.planarartifice.item.bauble;

import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import mod.emt.planarartifice.item.BaseItemPA;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBaublePA extends BaseItemPA implements IBauble {
    public AbstractBaublePA(String name, IRarity rarity) {
        super(name, rarity);
        this.setMaxStackSize(1);
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, @NotNull EntityPlayer playerIn, @NotNull EnumHand handIn) {
        ItemStack heldStack = playerIn.getHeldItem(handIn);
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(playerIn);
        for(int slots = 0; slots < handler.getSlots(); slots++) {
            ItemStack slotStack = handler.getStackInSlot(slots);
            if(slotStack.isEmpty() && handler.isItemValidForSlot(slots, heldStack, playerIn) && handler.insertItem(slots, heldStack, true).isEmpty()) {
                return new ActionResult<>(EnumActionResult.SUCCESS, handler.insertItem(slots, heldStack, false));
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
