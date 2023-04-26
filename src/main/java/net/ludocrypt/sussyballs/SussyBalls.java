package net.ludocrypt.sussyballs;

import java.util.List;
import java.util.Set;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries;
import net.minecraft.block.SuspiciousStewIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemStackSet;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class SussyBalls implements ModInitializer {

	public static final String MODID = "sussyballs";
	public static final Item SUSPICIOUS_SNOWBALL = Registry.register(Registries.ITEM, new Identifier(MODID, "suspicious_snowball"), new SuspiciousSnowball(new Item.Settings().maxCount(1)));
	public static final RecipeSerializer<SuspiciousSnowballRecipe> SUSPICIOUS_SNOWBALL_RECIPE = Registry.register(Registries.RECIPE_SERIALIZER,
			new Identifier(MODID, "crafting_special_suspicioussnowball"), new SpecialRecipeSerializer<SuspiciousSnowballRecipe>(SuspiciousSnowballRecipe::new));

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(new ModifyEntries() {

			@Override
			public void modifyEntries(FabricItemGroupEntries entries) {
				List<SuspiciousStewIngredient> list = SuspiciousStewIngredient.getAll();
				Set<ItemStack> set = ItemStackSet.create();

				for (SuspiciousStewIngredient suspiciouseffectholder : list) {
					ItemStack itemstack = new ItemStack(SUSPICIOUS_SNOWBALL);
					SuspiciousSnowball.addEffectToStew(itemstack, suspiciouseffectholder.getEffectInStew(), suspiciouseffectholder.getEffectInStewDuration());
					set.add(itemstack);
				}

				entries.addAll(set);
			}

		});
	}
}
