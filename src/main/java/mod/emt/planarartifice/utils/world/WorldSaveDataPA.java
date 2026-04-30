package mod.emt.planarartifice.utils.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.aspects.Aspect;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldSaveDataPA extends WorldSavedData {
    public static final String ID = "PlanarArtifice-SaveData";
    private final Map<UUID, MirroredJarData> jarData = new HashMap<>();

    public WorldSaveDataPA(String name) {
        super(name);
    }

    public WorldSaveDataPA() {
        this(ID);
    }

    @Nullable
    public MirroredJarData getJarData(UUID linkUUID) {
        return this.jarData.getOrDefault(linkUUID, null);
    }

    public void setJarData(UUID linkUUID, Aspect aspect, int amount) {
        if(this.jarData.containsKey(linkUUID)) {
            this.jarData.get(linkUUID).setAspect(aspect, amount);
        } else {
            this.jarData.put(linkUUID, new MirroredJarData(this, aspect, amount));
        }
        this.markDirty();
    }

    public void removeJarData(UUID linkUUID) {
        this.jarData.remove(linkUUID);
        this.markDirty();
    }

    public void cleanJarData() {
        if(this.jarData.values().removeIf(dataEntry -> dataEntry != null && (dataEntry.getAspect() == null || dataEntry.getAmount() <= 0))) {
            this.markDirty();
        }
    }

    public void deleteJarData() {
        this.jarData.clear();
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagCompound jarTagData = nbt.getCompoundTag("jarData");
        for(String key : jarTagData.getKeySet()) {
            UUID linkUUID = UUID.fromString(key);
            MirroredJarData data = new MirroredJarData(this, jarTagData.getCompoundTag(key));
            this.jarData.put(linkUUID, data);
        }
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.cleanJarData();
        NBTTagCompound jarTagData = new NBTTagCompound();
        this.jarData.forEach((key, data) -> jarTagData.setTag(key.toString(), data.serializeNBT()));
        compound.setTag("jarData", jarTagData);
        return compound;
    }
}
