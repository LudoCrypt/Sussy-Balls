package net.ludocrypt.sussyballs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;

public class SuspiciousSnowballRecipe extends CustomRecipe {

	public SuspiciousSnowballRecipe(ResourceLocation id) {
		super(id);
	}

	@Override
	public boolean matches(CraftingContainer container, Level level) {
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack itemstack = container.getItem(i);
			if (!itemstack.isEmpty()) {
				if (itemstack.is(Blocks.BROWN_MUSHROOM.asItem()) && !flag2) {
					flag2 = true;
				} else if (itemstack.is(Blocks.RED_MUSHROOM.asItem()) && !flag1) {
					flag1 = true;
				} else if ((itemstack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof FlowerBlock) && !flag) {
					flag = true;
				} else {
					if (!(itemstack.is(Items.SNOWBALL) || itemstack.is(SussyBalls.SUSPICIOUS_SNOWBALL.get())) || flag3) {
						return false;
					}

					flag3 = true;
				}
			}
		}

		return flag && flag2 && flag1 && flag3;
	}

	@Override
	public ItemStack assemble(CraftingContainer container) {
		ItemStack cocktailStack = null;
		for (int i = 0; i < container.getContainerSize(); ++i) {
			if (container.getItem(i).is(SussyBalls.SUSPICIOUS_SNOWBALL.get())) {
				cocktailStack = container.getItem(i);
				break;
			}
		}

		ItemStack suspiciousSnowball = cocktailStack != null ? cocktailStack.copy() : new ItemStack(SussyBalls.SUSPICIOUS_SNOWBALL.get(), 1);

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);
			if (!stack.isEmpty()) {

				if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof FlowerBlock flowerBlock) {
					MobEffect mobeffect = flowerBlock.getSuspiciousStewEffect();
					SuspiciousSnowball.addEffectToStew(suspiciousSnowball, mobeffect, flowerBlock.getEffectDuration());
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
	public RecipeSerializer<?> getSerializer() {
		return SussyBalls.SUSPICIOUS_SNOWBALL_RECIPE.get();
	}
}
