package mod.emt.planarartifice.tile;

import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.tiles.devices.TileMirrorEssentia;

public class TileFlawlessMirrorEssentia extends TileMirrorEssentia {
    //The ticks for each stabilization operation. Default Thaumcraft is 100.
    public int ticksToStabilize = 20;
    //The amount of essentia transferred before 1 flux is generated. Thaumcraft default is 64.
    public int instabilityThreshold = 512;

    @Override
    public void checkInstability() {
        if (this.instability > this.instabilityThreshold) {
            AuraHelper.polluteAura(this.world, this.pos, 1.0F, true);
            this.instability -= this.instabilityThreshold;
            this.markDirty();
        }

        if (this.instability > 0 && this.world.getTotalWorldTime() % (long) this.ticksToStabilize == 0) {
            --this.instability;
        }
    }
}
