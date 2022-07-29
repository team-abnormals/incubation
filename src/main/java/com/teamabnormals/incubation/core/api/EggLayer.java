package com.teamabnormals.incubation.core.api;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;

public interface EggLayer {
	int getEggTimer();

	void setEggTimer(int time);

	boolean isBirdJockey();

	Item getEggItem();

	int getNextEggTime(RandomSource rand);

	SoundEvent getEggLayingSound();
}
