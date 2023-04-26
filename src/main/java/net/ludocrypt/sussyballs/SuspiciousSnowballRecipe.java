package net.ludocrypt.sussyballs;

import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SuspiciousSnowballRecipe extends SpecialCraftingRecipe {

	public SuspiciousSnowballRecipe(Identifier id) {
		super(id);
	}

	@Override
	public boolean matches(CraftingInventory container, World world) {
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;

		for (int i = 0; i < container.size(); ++i) {
			ItemStack itemstack = container.getStack(i);
			if (!itemstack.isEmpty()) {
				if (itemstack.isOf(Blocks.BROWN_MUSHROOM.asItem()) && !flag2) {
					flag2 = true;
				} else if (itemstack.isOf(Blocks.RED_MUSHROOM.asItem()) && !flag1) {
					flag1 = true;
				} else if ((itemstack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof FlowerBlock) && !flag) {
					flag = true;
				} else {
					if (!(itemstack.isOf(Items.SNOWBALL) || itemstack.isOf(SussyBalls.SUSPICIOUS_SNOWBALL)) || flag3) {
						return false;
					}

					flag3 = true;
				}
			}
		}

		return flag && flag2 && flag1 && flag3;
	}

	@Override
	public ItemStack craft(CraftingInventory container) {
		ItemStack cocktailStack = null;
		for (int i = 0; i < container.size(); ++i) {
			if (container.getStack(i).isOf(SussyBalls.SUSPICIOUS_SNOWBALL)) {
				cocktailStack = container.getStack(i);
				break;
			}
		}

		ItemStack suspiciousSnowball = cocktailStack != null ? cocktailStack.copy() : new ItemStack(SussyBalls.SUSPICIOUS_SNOWBALL, 1);

		for (int i = 0; i < container.size(); ++i) {
			ItemStack stack = container.getStack(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof FlowerBlock flowerBlock) {
					StatusEffect mobeffect = flowerBlock.getEffectInStew();
					SuspiciousSnowball.addEffectToStew(suspiciousSnowball, mobeffect, flowerBlock.getEffectInStewDuration());
				}
			}
		}

		return suspiciousSnowball;
	}

	@Override
	public boolean fits(int x, int y) {
		return x >= 2 && y >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SussyBalls.SUSPICIOUS_SNOWBALL_RECIPE;
	}

}
