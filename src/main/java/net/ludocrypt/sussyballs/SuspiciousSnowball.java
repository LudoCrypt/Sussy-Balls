package net.ludocrypt.sussyballs;

import java.util.function.Consumer;

import net.minecraft.item.ItemStack;
import net.minecraft.item.SnowballItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class SuspiciousSnowball extends SnowballItem {

	public SuspiciousSnowball(Properties properties) {
		super(properties);
	}

	public static void addEffectToStew(ItemStack stack, Effect effect, int duration) {
		CompoundNBT tag = stack.getOrCreateTag();
		ListNBT list = tag.getList("Effects", 10);
		CompoundNBT effectTag = new CompoundNBT();
		effectTag.putInt("EffectId", Effect.getId(effect));
		effectTag.putInt("EffectDuration", duration);
		list.add(effectTag);
		tag.put("Effects", list);
	}

	public static void listPotionEffects(ItemStack stack, Consumer<EffectInstance> consumer) {
		CompoundNBT tag = stack.getTag();
		if (tag != null && tag.contains("Effects", 9)) {
			ListNBT list = tag.getList("Effects", 10);

			for (int i = 0; i < list.size(); ++i) {
				CompoundNBT effectTag = list.getCompound(i);
				int j;
				if (effectTag.contains("EffectDuration", 3)) {
					j = effectTag.getInt("EffectDuration");
				} else {
					j = 160;
				}

				Effect effect = Effect.byId(effectTag.getInt("EffectId"));
				if (effect != null) {
					consumer.accept(new EffectInstance(effect, j));
				}
			}
		}
	}

}
