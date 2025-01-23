package com.teamabnormals.incubation.core.registry;

import com.teamabnormals.incubation.core.Incubation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class IncubationPaintingVariants {
	public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Incubation.MOD_ID);

	public static final RegistryObject<PaintingVariant> EGG = PAINTING_VARIANTS.register("egg", () -> new PaintingVariant(16, 16));
	public static final RegistryObject<PaintingVariant> CULPRIT = PAINTING_VARIANTS.register("culprit", () -> new PaintingVariant(64, 64));
}