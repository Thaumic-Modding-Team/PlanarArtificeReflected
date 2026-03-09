package mod.emt.planarartifice.registry;

import mod.emt.planarartifice.client.gui.GuiSmelterPA;
import mod.emt.planarartifice.inventory.containers.ContainerSmelterPA;
import mod.emt.planarartifice.tile.TileSmelterPA;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandlerPA implements IGuiHandler {
    public static final int ID_ALKIMIUM_SMELTERY = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){

        switch(ID) {
            case ID_ALKIMIUM_SMELTERY:
                return new ContainerSmelterPA(player, (TileSmelterPA) world.getTileEntity(new BlockPos(x, y, z)));
        }

        return null;

    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){

        switch(ID) {
            case ID_ALKIMIUM_SMELTERY:
                return new GuiSmelterPA(player, (TileSmelterPA) world.getTileEntity(new BlockPos(x, y, z)));
        }

        return null;
    }
}
