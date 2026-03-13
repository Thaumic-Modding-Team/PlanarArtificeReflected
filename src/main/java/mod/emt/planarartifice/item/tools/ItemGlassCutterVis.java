package mod.emt.planarartifice.item.tools;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.items.IRechargable;
import thaumcraft.api.items.RechargeHelper;

public class ItemGlassCutterVis extends ItemGlassCutter implements IRechargable {
    public ItemGlassCutterVis() {
        super("glass_cutter_vis");
        this.setMaxDamage(1);
    }

    @Override
    public void getSubItems(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack(this);
            this.setCharge(stack, this.getMaxCharge(stack, null));
            items.add(stack);
            items.add(new ItemStack(this));
        }
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, @NotNull ItemStack stack) {
        return (this.getEnergy(stack) > 0 || this.getCharge(stack) > 0) && super.canHarvestBlock(state, stack);
    }

    @Override
    public void setDamage(@NotNull ItemStack stack, int damage) {
        int energy = this.getEnergy(stack);
        int charge = this.getCharge(stack);
        if(energy >= damage) {
            this.setEnergy(stack, energy - damage);
        } else {
            while(damage > energy && charge > 0) {
                charge--;
                energy += 5;
            }
            if(energy >= damage) {
                this.setEnergy(stack, energy - damage);
                this.setCharge(stack, charge);
            } else {
                super.setDamage(stack, damage);
            }
        }
    }

    @Override
    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.RARE;
    }

    public int getEnergy(ItemStack stack) {
        return this.getStackTag(stack).getInteger("energy");
    }

    public void setEnergy(ItemStack stack, int energy) {
        this.getStackTag(stack).setInteger("energy", energy);
    }

    public int getCharge(ItemStack stack) {
        return this.getStackTag(stack).getInteger(RechargeHelper.NBT_TAG);
    }

    public void setCharge(ItemStack stack, int charge) {
        this.getStackTag(stack).setInteger(RechargeHelper.NBT_TAG, MathHelper.clamp(charge, 0, this.getMaxCharge(stack, null)));
    }

    public NBTTagCompound getStackTag(ItemStack stack) {
        if(stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }

    @Override
    public int getMaxCharge(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return 100;
    }

    @Override
    public EnumChargeDisplay showInHud(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return EnumChargeDisplay.PERIODIC;
    }
}
