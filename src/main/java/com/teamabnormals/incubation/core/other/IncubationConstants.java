package com.teamabnormals.incubation.core.other;

import net.minecraft.resources.ResourceLocation;

public class IncubationConstants {
	public static final String ENVIRONMENTAL = "environmental";
	public static final String AUTUMNITY = "autumnity";

	public static final ResourceLocation CHICKEN = ResourceLocation.withDefaultNamespace("chicken");
	public static final ResourceLocation DUCK = ResourceLocation.fromNamespaceAndPath(ENVIRONMENTAL, "duck");
	public static final ResourceLocation TURKEY = ResourceLocation.fromNamespaceAndPath(AUTUMNITY, "turkey");

	public static final ResourceLocation HAS_DUCK = ResourceLocation.fromNamespaceAndPath(ENVIRONMENTAL, "has_animal/duck");
	public static final ResourceLocation HAS_TURKEY = ResourceLocation.fromNamespaceAndPath(AUTUMNITY, "has_animal/turkey");

	public static final ResourceLocation DUCK_EGG = ResourceLocation.fromNamespaceAndPath(ENVIRONMENTAL, "duck_egg");
	public static final ResourceLocation TURKEY_EGG = ResourceLocation.fromNamespaceAndPath(AUTUMNITY, "turkey_egg");
}
