package mod.emt.planarartifice.utils.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import thaumcraft.api.aspects.Aspect;

import javax.annotation.Nullable;

public class MirroredJarData implements INBTSerializable<NBTTagCompound> {
    private final WorldSaveDataPA parent;
    private Aspect aspect;
    private int amount;

    public MirroredJarData(WorldSaveDataPA parent, Aspect aspect, int amount) {
        this.parent = parent;
        this.aspect = aspect;
        this.amount = amount;
    }

    public MirroredJarData(WorldSaveDataPA parent, NBTTagCompound tagCompound) {
        this.parent = parent;
        this.deserializeNBT(tagCompound);
    }

    public boolean isEmpty() {
        return this.aspect == null || this.amount <= 0;
    }

    @Nullable
    public Aspect getAspect() {
        return this.aspect;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAspect(Aspect aspect, int amount) {
        this.aspect = aspect;
        this.amount = amount;
        this.parent.markDirty();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("aspect", this.aspect != null ? this.aspect.getTag() : "");
        tag.setInteger("amount", this.amount);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.aspect = Aspect.getAspect(nbt.getString("aspect"));
        this.amount = nbt.getInteger("amount");
    }
}
