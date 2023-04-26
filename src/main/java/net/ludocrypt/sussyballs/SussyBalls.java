package net.ludocrypt.sussyballs;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SussyBalls implements ModInitializer {

	public static final String MODID = "sussyballs";
	public static final Item SUSPICIOUS_SNOWBALL = Registry.register(Registry.ITEM, new Identifier(MODID, "suspicious_snowball"), new SuspiciousSnowball(new Item.Settings().maxCount(1)));
	public static final RecipeSerializer<SuspiciousSnowballRecipe> SUSPICIOUS_SNOWBALL_RECIPE = Registry.register(Registry.RECIPE_SERIALIZER,
			new Identifier(MODID, "crafting_special_suspicioussnowball"), new SpecialRecipeSerializer<SuspiciousSnowballRecipe>(SuspiciousSnowballRecipe::new));

	@Override
	public void onInitialize() {

	}
}
