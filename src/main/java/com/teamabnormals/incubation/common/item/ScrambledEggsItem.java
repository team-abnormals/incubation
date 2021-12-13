package com.teamabnormals.incubation.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class ScrambledEggsItem extends BowlFoodItem {
	public ScrambledEggsItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		entityLiving.heal(2.0F);
		return super.finishUsingItem(stack, worldIn, entityLiving);
	}
}