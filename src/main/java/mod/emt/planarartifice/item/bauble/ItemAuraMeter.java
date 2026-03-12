package mod.emt.planarartifice.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import mod.emt.planarartifice.registry.ModItemsPA;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.casters.ICaster;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.misc.PacketAuraToClient;
import thaumcraft.common.world.aura.AuraChunk;
import thaumcraft.common.world.aura.AuraHandler;

import java.util.HashSet;
import java.util.Set;

public class ItemAuraMeter extends AbstractBaublePA implements IBauble {
    public static Set<Item> GUI_ITEMS = new HashSet<>();

    public ItemAuraMeter() {
        super("aura_meter", EnumRarity.RARE);
        this.setMaxStackSize(1);
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.TRINKET;
    }

    @Override
    public void onUpdate(@NotNull ItemStack stack, World world, @NotNull Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && entity.ticksExisted % 20 == 0 && entity instanceof EntityPlayerMP)
            updateAura(stack, world, (EntityPlayerMP) entity);
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        this.onUpdate(itemstack, player.world, player, 0, true);
    }

    private void updateAura(ItemStack stack, World world, EntityPlayerMP player) {
        AuraChunk ac = AuraHandler.getAuraChunk(world.provider.getDimension(), player.getPosition().getX() >> 4, player.getPosition().getZ() >> 4);
        if (ac != null)
            PacketHandler.INSTANCE.sendTo(new PacketAuraToClient(ac), player);
    }

    public static boolean shouldRenderHud(EntityPlayer player) {
        if (GUI_ITEMS.contains(player.getHeldItemMainhand().getItem()) || GUI_ITEMS.contains(player.getHeldItemOffhand().getItem())) {
            return false;
        }

        if (player.getHeldItemMainhand().getItem() instanceof ICaster || player.getHeldItemOffhand().getItem() instanceof ICaster)
            return false;

        if (BaublesApi.isBaubleEquipped(player, ModItemsPA.AURA_METER) != -1) {
            return true;
        }

        for (int i = 0; i != player.inventory.getSizeInventory(); i++) {
            if (player.inventory.getStackInSlot(i).getItem() == ModItemsPA.AURA_METER) {
                return true;
            }
        }

        return false;
    }
}
