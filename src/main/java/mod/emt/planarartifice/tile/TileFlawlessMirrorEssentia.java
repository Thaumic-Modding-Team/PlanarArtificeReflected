package mod.emt.planarartifice.tile;

import mod.emt.planarartifice.config.ConfigHandlerPA;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.tiles.devices.TileMirrorEssentia;

public class TileFlawlessMirrorEssentia extends TileMirrorEssentia {
    @Override
    public void checkInstability() {
        int threshold = ConfigHandlerPA.flawlessMirrorEssentia.instabilityThreshold;
        if (threshold > 0 && this.instability > threshold) {
            AuraHelper.polluteAura(this.world, this.pos, 1.0F, true);
            this.instability -= threshold;
            this.markDirty();
        }

        if (this.instability > 0 && this.world.getTotalWorldTime() % (long) ConfigHandlerPA.flawlessMirrorEssentia.stabilizesTicks == 0) {
            --this.instability;
        }
    }
}
