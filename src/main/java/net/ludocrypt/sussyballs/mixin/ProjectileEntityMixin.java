package net.ludocrypt.sussyballs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.ludocrypt.sussyballs.SuspiciousSnowball;
import net.ludocrypt.sussyballs.SussyBalls;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.EntityHitResult;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {

	@Inject(method = "onEntityHit", at = @At("HEAD"))
	private void sussyballs$onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
		if ((ProjectileEntity) (Object) this instanceof SnowballEntity snowball) {
			if (snowball.getStack().isOf(SussyBalls.SUSPICIOUS_SNOWBALL)) {
				if (entityHitResult.getEntity() instanceof LivingEntity living) {
					SuspiciousSnowball.listPotionEffects(snowball.getStack(), living::addStatusEffect);
				}
			}
		}
	}

}
