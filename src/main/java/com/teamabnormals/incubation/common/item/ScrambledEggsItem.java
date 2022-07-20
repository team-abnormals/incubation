package com.teamabnormals.incubation.common.item;

import java.util.Random;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ScrambledEggsItem extends BowlFoodItem {
	public ScrambledEggsItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		entityLiving.heal(2.0F);
		Random rand = entityLiving.getRandom();
		if (worldIn.isClientSide()) {
			for (int i = 0; i < 4; ++i) {
				double d0 = rand.nextGaussian() * 0.02D;
				double d1 = rand.nextGaussian() * 0.02D;
				double d2 = rand.nextGaussian() * 0.02D;
				worldIn.addParticle(ParticleTypes.HEART, entityLiving.getRandomX(1.0D), entityLiving.getRandomY() + 0.5D, entityLiving.getRandomZ(1.0D), d0, d1, d2);
			}
		}
		return super.finishUsingItem(stack, worldIn, entityLiving);
	}
}