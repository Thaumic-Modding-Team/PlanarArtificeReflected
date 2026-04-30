package mod.emt.planarartifice.tile;

import mod.emt.planarartifice.utils.helper.WorldHelper;
import mod.emt.planarartifice.utils.world.MirroredJarData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.tiles.essentia.TileJarFillable;

import java.util.UUID;

public class TileMirroredJar extends TileJarFillable {
    private UUID linkUUID;

    @Override
    public void readSyncNBT(NBTTagCompound compound) {
        super.readSyncNBT(compound);
        if(compound.hasKey("linkUUID")) {
            this.linkUUID = UUID.fromString(compound.getString("linkUUID"));
        }
    }

    @Override
    public NBTTagCompound writeSyncNBT(NBTTagCompound compound) {
        super.writeSyncNBT(compound);
        if(this.linkUUID != null) {
            compound.setString("linkUUID", this.linkUUID.toString());
        }
        return compound;
    }

    @Override
    public void update() {
        this.syncJarData();
        super.update();
        this.updateJarData();
    }

    public boolean isLinked() {
        return this.linkUUID != null;
    }

    public void setLinkUUID(UUID uuid) {
        if(this.linkUUID != null && !this.linkUUID.equals(uuid)) {
            WorldHelper.removeMirroredJarData(this.linkUUID);
            this.linkUUID = null;
        }

        if(this.linkUUID == null) {
            this.linkUUID = uuid;
            this.syncJarData();
            this.syncTile(false);
            this.markDirty();
        }
    }

    public UUID getLinkUUID() {
        return this.linkUUID;
    }

    /**
     * Updates the contents of this jar to match the jar save data.
     */
    public void syncJarData() {
        if(!this.world.isRemote && this.linkUUID != null) {
            MirroredJarData data = WorldHelper.getMirroredJarData(this.linkUUID);
            if(data != null) {
                boolean needsUpdate = false;
                if (this.aspect != data.getAspect()) {
                    this.aspect = data.getAspect();
                    needsUpdate = true;
                }
                if(this.amount != data.getAmount()) {
                    this.amount = data.getAmount();
                    needsUpdate = true;
                }
                if(needsUpdate) {
                    this.syncTile(false);
                    this.markDirty();
                }
            } else {
                WorldHelper.setMirroredJarData(this.linkUUID, this.aspect, this.amount);
            }
        }
    }

    /**
     * Updates the jar save data to match the contents of this jar.
     */
    public void updateJarData() {
        if(!this.world.isRemote && this.linkUUID != null) {
            MirroredJarData data = WorldHelper.getMirroredJarData(this.linkUUID);
            if(data != null) {
                if(data.getAspect() != this.aspect || data.getAmount() != this.amount) {
                    data.setAspect(this.aspect, this.amount);
                }
            }
        }
    }

    @Override
    public AspectList getAspects() {
        this.syncJarData();
        return super.getAspects();
    }

    @Override
    public void setAspects(AspectList aspects) {
        super.setAspects(aspects);
        this.updateJarData();
    }

    @Override
    public int addToContainer(Aspect tt, int am) {
        if (am == 0) {
            return 0;
        } else {
            if (this.amount < 250 && tt == this.aspect || this.amount == 0) {
                this.aspect = tt;
                int added = Math.min(am, 250 - this.amount);
                this.amount += added;
                am -= added;
            }
            this.updateJarData();
            this.syncTile(false);
            this.markDirty();
            return am;
        }
    }

    @Override
    public boolean takeFromContainer(Aspect tt, int am) {
        if (this.amount >= am && tt == this.aspect) {
            this.amount -= am;
            if (this.amount <= 0) {
                this.aspect = null;
                this.amount = 0;
            }

            this.updateJarData();
            this.syncTile(false);
            this.markDirty();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amt) {
        this.syncJarData();
        return super.doesContainerContainAmount(tag, amt);
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        this.syncJarData();
        return super.doesContainerContain(ot);
    }

    @Override
    public int containerContains(Aspect tag) {
        this.syncJarData();
        return super.containerContains(tag);
    }

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        this.syncJarData();
        return super.doesContainerAccept(tag);
    }

    @Override
    public Aspect getSuctionType(EnumFacing loc) {
        this.syncJarData();
        return super.getSuctionType(loc);
    }

    @Override
    public int getSuctionAmount(EnumFacing loc) {
        this.syncJarData();
        return super.getSuctionAmount(loc);
    }

    @Override
    public Aspect getEssentiaType(EnumFacing loc) {
        this.syncJarData();
        return super.getEssentiaType(loc);
    }

    @Override
    public int getEssentiaAmount(EnumFacing loc) {
        this.syncJarData();
        return super.getEssentiaAmount(loc);
    }
}
