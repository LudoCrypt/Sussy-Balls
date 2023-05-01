package net.ludocrypt.sussyballs;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SussyBalls implements ModInitializer {

	public static final String MODID = "sussyballs";
	public static final Item SUSPICIOUS_SNOWBALL = Registry.register(Registry.ITEM, new Identifier(MODID, "suspicious_snowball"), new SuspiciousSnowball(new Item.Settings().maxCount(1)));
	public static final RecipeSerializer<SuspiciousSnowballRecipe> SUSPICIOUS_SNOWBALL_RECIPE = Registry.register(Registry.RECIPE_SERIALIZER,
			new Identifier(MODID, "crafting_special_suspicioussnowball"), new SpecialRecipeSerializer<SuspiciousSnowballRecipe>(SuspiciousSnowballRecipe::new));

	@Override
	public void onInitialize() {
		DispenserBlock.registerBehavior(SUSPICIOUS_SNOWBALL, new ProjectileDispenserBehavior() {

			@Override
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				return Util.make(new SnowballEntity(world, position.getX(), position.getY(), position.getZ()), entity -> entity.setItem(stack));
			}

		});
	}
}
