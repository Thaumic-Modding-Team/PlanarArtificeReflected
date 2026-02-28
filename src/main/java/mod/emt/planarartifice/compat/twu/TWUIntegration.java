package mod.emt.planarartifice.compat.twu;

import com.verdantartifice.thaumicwonders.common.items.ItemsTW;
import mod.emt.planarartifice.item.bauble.PAItemAuraMeter;

public class TWUIntegration {
    public static void init() {
        PAItemAuraMeter.GUI_ITEMS.add(ItemsTW.PRIMAL_DESTROYER);
    }
}
