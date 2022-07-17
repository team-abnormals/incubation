package com.teamabnormals.incubation.core.registry;

import com.teamabnormals.incubation.common.levelgen.feature.BirdNestFeature;
import com.teamabnormals.incubation.common.levelgen.feature.configurations.NestConfiguration;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.IncubationConstants;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@EventBusSubscriber(modid = Incubation.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IncubationFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Incubation.MOD_ID);

	public static final RegistryObject<Feature<NestConfiguration>> BIRD_NEST = FEATURES.register("bird_nest", () -> new BirdNestFeature(NestConfiguration.CODEC));

	public static final class IncubationConfiguredFeatures {
		public static final Holder<ConfiguredFeature<?, ?>> NEST_CHICKEN = register("nest_chicken", BIRD_NEST.get(), new NestConfiguration(IncubationBlocks.TWIG_CHICKEN_NEST.get().defaultBlockState(), IncubationConstants.CHICKEN));
		public static final Holder<ConfiguredFeature<?, ?>> NEST_DUCK = register("nest_duck", BIRD_NEST.get(), new NestConfiguration(IncubationBlocks.TWIG_DUCK_NEST.get().defaultBlockState(), IncubationConstants.DUCK));
		public static final Holder<ConfiguredFeature<?, ?>> NEST_TURKEY = register("nest_turkey", BIRD_NEST.get(), new NestConfiguration(IncubationBlocks.TWIG_TURKEY_NEST.get().defaultBlockState(), IncubationConstants.TURKEY));

		public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> register(String name, F feature, FC config) {
			return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Incubation.MOD_ID, name), new ConfiguredFeature<>(feature, config));
		}
	}

	public static final class IncubationPlacedFeatures {
		public static final Holder<PlacedFeature> NEST_CHICKEN = register("nest_chicken", IncubationConfiguredFeatures.NEST_CHICKEN, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final Holder<PlacedFeature> NEST_DUCK = register("nest_duck", IncubationConfiguredFeatures.NEST_DUCK, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final Holder<PlacedFeature> NEST_TURKEY = register("nest_turkey", IncubationConfiguredFeatures.NEST_TURKEY, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		public static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... placementModifiers) {
			return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Incubation.MOD_ID, name), new PlacedFeature(Holder.hackyErase(configuredFeature), List.of(placementModifiers)));
		}
	}
}
