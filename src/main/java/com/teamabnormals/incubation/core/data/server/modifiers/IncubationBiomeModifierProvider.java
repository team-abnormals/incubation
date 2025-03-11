package com.teamabnormals.incubation.core.data.server.modifiers;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.tags.IncubationBiomeTags;
import com.teamabnormals.incubation.core.registry.IncubationFeatures.IncubationPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IncubationBiomeModifierProvider {

	public static void bootstrap(BootstrapContext<BiomeModifier> context) {
		addFeature(context, "chicken_nest", IncubationBiomeTags.HAS_CHICKEN_NEST, GenerationStep.Decoration.VEGETAL_DECORATION, IncubationPlacedFeatures.NEST_CHICKEN);
		addFeature(context, "duck_nest", IncubationBiomeTags.HAS_DUCK_NEST, GenerationStep.Decoration.VEGETAL_DECORATION, IncubationPlacedFeatures.NEST_DUCK);
		addFeature(context, "turkey_nest", IncubationBiomeTags.HAS_TURKEY_NEST, GenerationStep.Decoration.VEGETAL_DECORATION, IncubationPlacedFeatures.NEST_TURKEY);
	}

	@SafeVarargs
	private static void addFeature(BootstrapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, Decoration step, ResourceKey<PlacedFeature>... features) {
		register(context, "add_feature/" + name, () -> new AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), featureSet(context, features), step));
	}

	@SafeVarargs
	private static HolderSet<PlacedFeature> featureSet(BootstrapContext<?> context, ResourceKey<PlacedFeature>... features) {
		return HolderSet.direct(Stream.of(features).map(key -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(key)).collect(Collectors.toList()));
	}

	private static void register(BootstrapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
		context.register(ResourceKey.create(Keys.BIOME_MODIFIERS, Incubation.location(name)), modifier.get());
	}
}