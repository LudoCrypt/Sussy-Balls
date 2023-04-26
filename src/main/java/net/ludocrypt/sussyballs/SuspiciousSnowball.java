package net.ludocrypt.sussyballs;

import java.util.function.Consumer;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SnowballItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class SuspiciousSnowball extends SnowballItem {

	public SuspiciousSnowball(Item.Settings properties) {
		super(properties);
	}

	public static void addEffectToStew(ItemStack stack, StatusEffect effect, int duration) {
		NbtCompound tag = stack.getOrCreateNbt();
		NbtList list = tag.getList("Effects", 10);
		NbtCompound effectTag = new NbtCompound();
		effectTag.putInt("EffectId", StatusEffect.getRawId(effect));
		effectTag.putInt("EffectDuration", duration);
		list.add(effectTag);
		tag.put("Effects", list);
	}

	public static void listPotionEffects(ItemStack stack, Consumer<StatusEffectInstance> consumer) {
		NbtCompound tag = stack.getNbt();
		if (tag != null && tag.contains("Effects", 9)) {
			NbtList list = tag.getList("Effects", 10);

			for (int i = 0; i < list.size(); ++i) {
				NbtCompound effectTag = list.getCompound(i);
				int j;
				if (effectTag.contains("EffectDuration", 3)) {
					j = effectTag.getInt("EffectDuration");
				} else {
					j = 160;
				}

				StatusEffect effect = StatusEffect.byRawId(effectTag.getInt("EffectId"));
				if (effect != null) {
					consumer.accept(new StatusEffectInstance(effect, j));
				}
			}
		}
	}

}
