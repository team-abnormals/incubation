package com.teamabnormals.incubation.core.registry.datapack;

import com.teamabnormals.incubation.core.Incubation;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class IncubationPaintingVariants {
	public static final ResourceKey<PaintingVariant> EGG = create("egg");
	public static final ResourceKey<PaintingVariant> CULPRIT = create("culprit");

	public static void bootstrap(BootstrapContext<PaintingVariant> context) {
		register(context, EGG, 1, 1);
		register(context, CULPRIT, 4, 4);
	}

	private static ResourceKey<PaintingVariant> create(String name) {
		return ResourceKey.create(Registries.PAINTING_VARIANT, Incubation.location(name));
	}

	private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> key, int width, int height) {
		context.register(key, new PaintingVariant(width, height, key.location()));
	}
}