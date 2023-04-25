package net.ludocrypt.sussyballs;

import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SuspiciousSnowballRecipe extends SpecialRecipe {

	public SuspiciousSnowballRecipe(ResourceLocation id) {
		super(id);
	}

	@Override
	public boolean matches(CraftingInventory container, World world) {
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack itemstack = container.getItem(i);
			if (!itemstack.isEmpty()) {
				if (itemstack.getItem().equals(Blocks.BROWN_MUSHROOM.asItem()) && !flag2) {
					flag2 = true;
				} else if (itemstack.getItem().equals(Blocks.RED_MUSHROOM.asItem()) && !flag1) {
					flag1 = true;
				} else if ((itemstack.getItem() instanceof BlockItem && ((BlockItem) itemstack.getItem()).getBlock() instanceof FlowerBlock) && !flag) {
					flag = true;
				} else {
					if (!(itemstack.getItem().equals(Items.SNOWBALL) || itemstack.getItem().equals(SussyBalls.SUSPICIOUS_SNOWBALL.get())) || flag3) {
						return false;
					}

					flag3 = true;
				}
			}
		}

		return flag && flag2 && flag1 && flag3;
	}

	@Override
	public ItemStack assemble(CraftingInventory container) {
		ItemStack cocktailStack = null;
		for (int i = 0; i < container.getContainerSize(); ++i) {
			if (container.getItem(i).getItem().equals(SussyBalls.SUSPICIOUS_SNOWBALL.get())) {
				cocktailStack = container.getItem(i);
				break;
			}
		}

		ItemStack suspiciousSnowball = cocktailStack != null ? cocktailStack.copy() : new ItemStack(SussyBalls.SUSPICIOUS_SNOWBALL.get(), 1);

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);
			if (!stack.isEmpty()) {

				if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof FlowerBlock) {
					FlowerBlock flowerblock = (FlowerBlock) ((BlockItem) stack.getItem()).getBlock();
					Effect effect = flowerblock.getSuspiciousStewEffect();
					SuspiciousSnowball.addEffectToStew(suspiciousSnowball, effect, flowerblock.getEffectDuration());
				}
			}
		}

		return suspiciousSnowball;
	}

	@Override
	public boolean canCraftInDimensions(int x, int y) {
		return x >= 2 && y >= 2;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return SussyBalls.SUSPICIOUS_SNOWBALL_RECIPE.get();
	}
}
