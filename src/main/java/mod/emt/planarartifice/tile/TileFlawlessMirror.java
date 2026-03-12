package mod.emt.planarartifice.tile;

import mod.emt.planarartifice.config.ConfigHandlerPA;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.tiles.devices.TileMirror;

public class TileFlawlessMirror extends TileMirror {
    public NonNullList<ItemStack> outputs = NonNullList.create();

    @Override
    public void eject() {
        if (!this.outputs.isEmpty()) {
            int i = this.world.rand.nextInt(this.outputs.size());
            if (!this.outputs.get(i).isEmpty()) {
                ItemStack outItem = this.outputs.get(i).splitStack(16);
                if (this.spawnItem(outItem)) {
                    this.addInstability(null, outItem.getCount());
                    this.world.addBlockEvent(this.getPos(), this.blockType, 1, 0);
                    if (this.outputs.get(i).getCount() <= 0) {
                        this.outputs.remove(i);
                    }
                }
            } else {
                this.outputs.remove(i);
            }
            this.markDirty();
        }
    }

    @Override
    public void checkInstability() {
        int threshold = ConfigHandlerPA.flawlessMirror.instabilityThreshold;
        if (threshold > 0 && this.instability > threshold) {
            AuraHelper.polluteAura(this.world, this.pos, 1.0F, true);
            this.instability -= threshold;
            this.markDirty();
        }

        if (this.instability > 0 && this.world.getTotalWorldTime() % (long) ConfigHandlerPA.flawlessMirror.stabilizesTicks == 0) {
            --this.instability;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList itemList = compound.getTagList("Items", 10);
        this.outputs = NonNullList.create();
        for(int i = 0; i < itemList.tagCount(); ++i) {
            NBTTagCompound itemTag = itemList.getCompoundTagAt(i);
            this.outputs.add(new ItemStack(itemTag));
        }
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList itemList = new NBTTagList();
        for (ItemStack stack : this.outputs) {
            if (!stack.isEmpty()) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemList.appendTag(stack.writeToNBT(itemTag));
            }
        }
        compound.setTag("Items", itemList);
        return compound;
    }

    @Override
    public void addStack(ItemStack stack) {
        if(!stack.isEmpty()) {
            this.outputs.add(stack);
            this.markDirty();
        }
    }
}
