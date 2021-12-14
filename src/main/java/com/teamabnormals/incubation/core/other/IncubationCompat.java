package com.teamabnormals.incubation.core.other;

import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import net.minecraft.world.item.Items;

public class IncubationCompat {

	public static void registerCompat() {
		registerCompostables();
		registerFlammables();
		DataUtil.changeItemLocalization(Items.EGG, Incubation.MOD_ID, "chicken_egg");
	}

	private static void registerCompostables() {
		DataUtil.registerCompostable(IncubationBlocks.TWIG_NEST.get(), 0.65F);
		DataUtil.registerCompostable(IncubationBlocks.HAY_NEST.get(), 0.65F);
	}

	private static void registerFlammables() {
		DataUtil.registerFlammable(IncubationBlocks.TWIG_NEST.get(), 60, 20);
		DataUtil.registerFlammable(IncubationBlocks.TWIG_CHICKEN_NEST.get(), 60, 20);
		DataUtil.registerFlammable(IncubationBlocks.TWIG_DUCK_NEST.get(), 60, 20);
		DataUtil.registerFlammable(IncubationBlocks.TWIG_TURKEY_NEST.get(), 60, 20);

		DataUtil.registerFlammable(IncubationBlocks.HAY_NEST.get(), 60, 20);
		DataUtil.registerFlammable(IncubationBlocks.HAY_CHICKEN_NEST.get(), 60, 20);
		DataUtil.registerFlammable(IncubationBlocks.HAY_DUCK_NEST.get(), 60, 20);
		DataUtil.registerFlammable(IncubationBlocks.HAY_TURKEY_NEST.get(), 60, 20);

		DataUtil.registerFlammable(IncubationBlocks.CHICKEN_EGG_CRATE.get(), 5, 20);
		DataUtil.registerFlammable(IncubationBlocks.TURTLE_EGG_CRATE.get(), 5, 20);
	}
}
