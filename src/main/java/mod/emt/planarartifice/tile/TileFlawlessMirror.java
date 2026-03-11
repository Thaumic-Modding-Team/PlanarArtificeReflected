package mod.emt.planarartifice.tile;

import mod.emt.planarartifice.config.ConfigHandlerPA;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.tiles.devices.TileMirror;

public class TileFlawlessMirror extends TileMirror {
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
}
