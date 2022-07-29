package com.teamabnormals.incubation.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.incubation.common.levelgen.feature.BirdNestFeature;
import com.teamabnormals.incubation.common.levelgen.feature.configurations.NestConfiguration;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.IncubationConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@EventBusSubscriber(modid = Incubation.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IncubationFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Incubation.MOD_ID);

	public static final RegistryObject<Feature<NestConfiguration>> BIRD_NEST = FEATURES.register("bird_nest", () -> new BirdNestFeature(NestConfiguration.CODEC));

	public static final class IncubationConfiguredFeatures {
		public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Incubation.MOD_ID);

		public static final RegistryObject<ConfiguredFeature<NestConfiguration, ?>> NEST_CHICKEN = register("nest_chicken", () -> new ConfiguredFeature<>(BIRD_NEST.get(), new NestConfiguration(IncubationBlocks.TWIG_CHICKEN_NEST.get().defaultBlockState(), IncubationConstants.CHICKEN)));
		public static final RegistryObject<ConfiguredFeature<NestConfiguration, ?>> NEST_DUCK = register("nest_duck", () -> new ConfiguredFeature<>(BIRD_NEST.get(), new NestConfiguration(IncubationBlocks.TWIG_DUCK_NEST.get().defaultBlockState(), IncubationConstants.DUCK)));
		public static final RegistryObject<ConfiguredFeature<NestConfiguration, ?>> NEST_TURKEY = register("nest_turkey", () -> new ConfiguredFeature<>(BIRD_NEST.get(), new NestConfiguration(IncubationBlocks.TWIG_TURKEY_NEST.get().defaultBlockState(), IncubationConstants.TURKEY)));

		private static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, ?>> register(String name, Supplier<ConfiguredFeature<FC, F>> feature) {
			return CONFIGURED_FEATURES.register(name, feature);
		}
	}

	public static final class IncubationPlacedFeatures {
		public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Incubation.MOD_ID);

		public static final RegistryObject<PlacedFeature> NEST_CHICKEN = register("nest_chicken", IncubationConfiguredFeatures.NEST_CHICKEN, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> NEST_DUCK = register("nest_duck", IncubationConfiguredFeatures.NEST_DUCK, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> NEST_TURKEY = register("nest_turkey", IncubationConfiguredFeatures.NEST_TURKEY, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		@SuppressWarnings("unchecked")
		private static RegistryObject<PlacedFeature> register(String name, RegistryObject<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... placementModifiers) {
			return PLACED_FEATURES.register(name, () -> new PlacedFeature((Holder<ConfiguredFeature<?, ?>>) feature.getHolder().get(), ImmutableList.copyOf(placementModifiers)));
		}
	}
}
