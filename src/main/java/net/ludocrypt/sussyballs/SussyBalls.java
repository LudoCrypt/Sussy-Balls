package net.ludocrypt.sussyballs;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(SussyBalls.MODID)
public class SussyBalls {

	public static final String MODID = "sussyballs";

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<Item> SUSPICIOUS_SNOWBALL = ITEMS.register("suspicious_snowball", () -> new SuspiciousSnowball(new Item.Properties().stacksTo(1)));

	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SEIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

	public static final RegistryObject<IRecipeSerializer<SuspiciousSnowballRecipe>> SUSPICIOUS_SNOWBALL_RECIPE = RECIPE_SEIALIZERS.register("crafting_special_suspicioussnowball",
			() -> new SpecialRecipeSerializer<>(SuspiciousSnowballRecipe::new));

	public SussyBalls() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ITEMS.register(modEventBus);
		RECIPE_SEIALIZERS.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onProjectileImpact(ProjectileImpactEvent event) {
		if (event.getEntity() instanceof SnowballEntity) {
			SnowballEntity snowball = (SnowballEntity) event.getEntity();
			if (snowball.getItem().getItem().equals(SussyBalls.SUSPICIOUS_SNOWBALL.get())) {
				if (event.getRayTraceResult().getType().equals(RayTraceResult.Type.ENTITY)) {
					EntityRayTraceResult hit = (EntityRayTraceResult) event.getRayTraceResult();
					if (hit.getEntity() instanceof LivingEntity) {
						LivingEntity living = (LivingEntity) hit.getEntity();
						SuspiciousSnowball.listPotionEffects(snowball.getItem(), living::addEffect);
					}
				}
			}
		}
	}

}
