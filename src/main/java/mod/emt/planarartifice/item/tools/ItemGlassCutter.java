package mod.emt.planarartifice.item.tools;

import mod.emt.planarartifice.item.BaseItemPA;
import mod.emt.planarartifice.utils.helper.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ItemGlassCutter extends BaseItemPA {
    public static Method getSilkTouchDropMethod;

    public ItemGlassCutter(String name) {
        super(name);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
    }

    @Override
    public @NotNull EnumActionResult onItemUse(@NotNull EntityPlayer player, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        if(state.getBlock().canHarvestBlock(worldIn, pos, player)) {
            if(!worldIn.isRemote && this.canHarvestBlock(state, stack)) {
                ItemStack silkDrop = this.getSilkTouchDrop(state);
                if(!silkDrop.isEmpty()) {
                    worldIn.setBlockToAir(pos);
                    SoundType soundType = state.getBlock().getSoundType(state, worldIn, pos, player);
                    worldIn.playSound(null, pos, soundType.getBreakSound(), SoundCategory.PLAYERS, soundType.getVolume(), soundType.getPitch());
                    NonNullList<ItemStack> drops = NonNullList.from(ItemStack.EMPTY, silkDrop);
                    ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, 0, 1.0f, true, player);
                    for(ItemStack drop : drops) {
                        Block.spawnAsEntity(worldIn, pos, drop);
                    }
                    stack.damageItem(1, player);
                }
            }
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, @NotNull ItemStack stack) {
        return state.getMaterial() == Material.GLASS || super.canHarvestBlock(state, stack);
    }

    @Override
    public boolean getIsRepairable(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
        int quartzId = OreDictionary.getOreID("gemQuartz");
        return Arrays.stream(OreDictionary.getOreIDs(repair)).anyMatch(id -> id == quartzId) || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.planarartifice:glass_cutter.info"));
    }

    public ItemStack getSilkTouchDrop(IBlockState state) {
        //I'm too lazy to do access transformers or
        try {
            if(getSilkTouchDropMethod == null) {
                getSilkTouchDropMethod = ObfuscationReflectionHelper.findMethod(Block.class, "getSilkTouchDrop", ItemStack.class, IBlockState.class);
            }
            if(!getSilkTouchDropMethod.isAccessible()) {
                getSilkTouchDropMethod.setAccessible(true);
            }
            return (ItemStack) getSilkTouchDropMethod.invoke(state.getBlock(), state);
        } catch (Exception e) {
            LogHelper.error("Failed to access Block#getSilkTouchDrop(IBlockState).");
        }
        return ItemStack.EMPTY;
    }
}
