package net.ludocrypt.sussyballs;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(SussyBalls.MODID)
public class SussyBalls {

	public static final String MODID = "sussyballs";

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<Item> SUSPICIOUS_SNOWBALL = ITEMS.register("suspicious_snowball", () -> new SuspiciousSnowball(new Item.Properties().stacksTo(1)));

	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SEIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

	public static final RegistryObject<RecipeSerializer<SuspiciousSnowballRecipe>> SUSPICIOUS_SNOWBALL_RECIPE = RECIPE_SEIALIZERS.register("crafting_special_suspicioussnowball",
			() -> new SimpleRecipeSerializer<>(SuspiciousSnowballRecipe::new));

	public SussyBalls() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ITEMS.register(modEventBus);
		RECIPE_SEIALIZERS.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onProjectileImpact(ProjectileImpactEvent event) {
		if (event.getEntity() instanceof Snowball snowball) {
			if (snowball.getItem().is(SussyBalls.SUSPICIOUS_SNOWBALL.get())) {
				if (event.getRayTraceResult().getType().equals(HitResult.Type.ENTITY)) {
					EntityHitResult hit = (EntityHitResult) event.getRayTraceResult();
					if (hit.getEntity() instanceof LivingEntity living) {
						SuspiciousSnowball.listPotionEffects(snowball.getItem(), living::addEffect);
					}
				}
			}
		}
	}

}
