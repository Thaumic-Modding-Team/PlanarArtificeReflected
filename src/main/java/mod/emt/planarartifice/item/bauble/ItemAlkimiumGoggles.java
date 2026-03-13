package mod.emt.planarartifice.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import baubles.api.render.IRenderBauble;
import mod.emt.planarartifice.PlanarArtifice;
import mod.emt.planarartifice.registry.ModItemsPA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.items.IGoggles;
import thaumcraft.api.items.IVisDiscountGear;
import thaumcraft.client.lib.UtilsFX;

import java.util.Arrays;
import java.util.Objects;

public class ItemAlkimiumGoggles extends ItemArmor implements IBauble, IRenderBauble, IGoggles, IVisDiscountGear {
    public static final ResourceLocation GOGGLES_TEXTURE = new ResourceLocation(PlanarArtifice.MOD_ID, "textures/models/baubles/alkimium_goggles_bauble.png");
    public static ArmorMaterial ALKIMIUM_MATERIAL = EnumHelper.addArmorMaterial("ALKIMIUM", "ALKIMIUM", 25, new int[]{3, 7, 5, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2);

    public ItemAlkimiumGoggles() {
        super(ALKIMIUM_MATERIAL, 3, EntityEquipmentSlot.HEAD);
        this.setRegistryName(PlanarArtifice.MOD_ID, "alkimium_goggles");
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
        this.setCreativeTab(PlanarArtifice.tabPA);
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, @NotNull EntityPlayer playerIn, @NotNull EnumHand handIn) {
        ItemStack heldStack = playerIn.getHeldItem(handIn);
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(playerIn);
        for (int slots = 0; slots < handler.getSlots(); slots++) {
            ItemStack slotStack = handler.getStackInSlot(slots);
            if (slotStack.isEmpty() && handler.isItemValidForSlot(slots, heldStack, playerIn) && handler.insertItem(slots, heldStack, true).isEmpty()) {
                return new ActionResult<>(EnumActionResult.SUCCESS, handler.insertItem(slots, heldStack, false));
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public @Nullable String getArmorTexture(@NotNull ItemStack stack, @NotNull Entity entity, @NotNull EntityEquipmentSlot slot, @NotNull String type) {
        return "planarartifice:textures/models/armor/alkimium_goggles.png";
    }

    @Override
    public boolean getIsRepairable(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
        int alkimiumId = OreDictionary.getOreID("ingotAlkimium");
        return Arrays.stream(OreDictionary.getOreIDs(repair)).anyMatch(id -> id == alkimiumId) || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.HEAD;
    }

    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float v) {
        if (type == IRenderBauble.RenderType.HEAD) {
            player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            Minecraft.getMinecraft().renderEngine.bindTexture(GOGGLES_TEXTURE);
            IRenderBauble.Helper.translateToHeadLevel(player);
            IRenderBauble.Helper.translateToFace();
            IRenderBauble.Helper.defaultTransforms();
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5D, -0.5D, 0.11999999731779099D);
            UtilsFX.renderTextureIn3D(0.0F, 0.0F, 1.0F, 1.0F, 16, 26, 0.1F);
        }
    }

    @Override
    public boolean showIngamePopups(ItemStack stack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public int getVisDiscount(ItemStack stack, EntityPlayer player) {
        return 8;
    }

    public static boolean shouldRenderHud(EntityPlayer player) {
        return player.inventory.armorItemInSlot(3).getItem() == ModItemsPA.ALKIMIUM_GOGGLES
                || BaublesApi.isBaubleEquipped(player, ModItemsPA.ALKIMIUM_GOGGLES) != -1;
    }
}
