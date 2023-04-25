package net.ludocrypt.sussyballs;

import java.util.function.Consumer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraftforge.common.ForgeHooks;

public class SuspiciousSnowball extends SnowballItem {

	public SuspiciousSnowball(Properties properties) {
		super(properties);
	}

	public static void addEffectToStew(ItemStack stack, MobEffect effect, int duration) {
		CompoundTag tag = stack.getOrCreateTag();
		ListTag list = tag.getList("Effects", 10);
		CompoundTag effectTag = new CompoundTag();
		effectTag.putInt("EffectId", MobEffect.getId(effect));
		ForgeHooks.saveMobEffect(effectTag, "forge:effect_id", effect);
		effectTag.putInt("EffectDuration", duration);
		list.add(effectTag);
		tag.put("Effects", list);
	}

	public static void listPotionEffects(ItemStack stack, Consumer<MobEffectInstance> consumer) {
		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains("Effects", 9)) {
			ListTag list = tag.getList("Effects", 10);

			for (int i = 0; i < list.size(); ++i) {
				CompoundTag effectTag = list.getCompound(i);
				int j;
				if (effectTag.contains("EffectDuration", 3)) {
					j = effectTag.getInt("EffectDuration");
				} else {
					j = 160;
				}

				MobEffect effect = MobEffect.byId(effectTag.getInt("EffectId"));
				effect = ForgeHooks.loadMobEffect(effectTag, "forge:effect_id", effect);
				if (effect != null) {
					consumer.accept(new MobEffectInstance(effect, j));
				}
			}
		}
	}

}
