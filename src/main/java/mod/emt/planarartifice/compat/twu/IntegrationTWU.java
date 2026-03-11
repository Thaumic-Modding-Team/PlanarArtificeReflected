package mod.emt.planarartifice.compat.twu;

import com.verdantartifice.thaumicwonders.common.items.ItemsTW;
import mod.emt.planarartifice.item.bauble.ItemAuraMeter;

public class IntegrationTWU {
    public static void init() {
        ItemAuraMeter.GUI_ITEMS.add(ItemsTW.PRIMAL_DESTROYER);
    }
}
