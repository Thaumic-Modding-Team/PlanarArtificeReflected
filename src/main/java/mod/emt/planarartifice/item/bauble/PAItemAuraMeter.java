package mod.emt.planarartifice.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import mod.emt.planarartifice.item.BaseItemPA;
import mod.emt.planarartifice.registry.ModItemsPA;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import thaumcraft.api.casters.ICaster;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.misc.PacketAuraToClient;
import thaumcraft.common.world.aura.AuraChunk;
import thaumcraft.common.world.aura.AuraHandler;

public class PAItemAuraMeter extends BaseItemPA implements IBauble {
    public PAItemAuraMeter() {
        super(EnumRarity.RARE);
        this.setMaxStackSize(1);
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.BODY;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
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
            PacketHandler.INSTANCE.sendTo((IMessage) new PacketAuraToClient(ac), player);
    }

    // TODO: Definitely should improve how these are checked to account for addons (e.g. Thaumic Wonders Unofficial)
    public static boolean shouldRenderHud(EntityPlayer player) {
        if (player.getHeldItemMainhand().getItem() == ItemsTC.thaumometer || player.getHeldItemOffhand().getItem() == ItemsTC.thaumometer)
            return false;

        if (player.getHeldItemMainhand().getItem() == ItemsTC.sanityChecker || player.getHeldItemOffhand().getItem() == ItemsTC.sanityChecker)
            return false;

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
