package mod.emt.planarartifice.tile;

import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.tiles.devices.TileMirror;

public class TileFlawlessMirror extends TileMirror {
    //The ticks for each stabilization operation. Default Thaumcraft is 100.
    public static int ticksToStabilize = 20;
    //The number of items transferred before 1 flux is generated. Thaumcraft default is 128.
    public static int instabilityThreshold = 512;

    @Override
    public void checkInstability() {
        if (this.instability > instabilityThreshold) {
            AuraHelper.polluteAura(this.world, this.pos, 1.0F, true);
            this.instability -= instabilityThreshold;
            this.markDirty();
        }

        if (this.instability > 0 && this.world.getTotalWorldTime() % (long) ticksToStabilize == 0) {
            --this.instability;
        }
    }
}
