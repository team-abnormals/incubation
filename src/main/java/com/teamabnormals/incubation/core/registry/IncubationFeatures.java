package com.teamabnormals.incubation.core.registry;

import com.teamabnormals.incubation.common.levelgen.feature.BirdNestFeature;
import com.teamabnormals.incubation.common.levelgen.feature.configurations.NestConfiguration;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.IncubationConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Incubation.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IncubationFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Incubation.MOD_ID);

	public static final RegistryObject<Feature<NestConfiguration>> BIRD_NEST = FEATURES.register("bird_nest", () -> new BirdNestFeature(NestConfiguration.CODEC));

	public static final class IncubationConfiguredFeatures {
		public static final ResourceKey<ConfiguredFeature<?, ?>> NEST_CHICKEN = createKey("nest_chicken");
		public static final ResourceKey<ConfiguredFeature<?, ?>> NEST_DUCK = createKey("nest_duck");
		public static final ResourceKey<ConfiguredFeature<?, ?>> NEST_TURKEY = createKey("nest_turkey");

		public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
			register(context, NEST_CHICKEN, () -> new ConfiguredFeature<>(BIRD_NEST.get(), new NestConfiguration(IncubationBlocks.TWIG_CHICKEN_NEST.get().defaultBlockState(), IncubationConstants.CHICKEN)));
			register(context, NEST_DUCK, () -> new ConfiguredFeature<>(BIRD_NEST.get(), new NestConfiguration(IncubationBlocks.TWIG_DUCK_NEST.get().defaultBlockState(), IncubationConstants.DUCK)));
			register(context, NEST_TURKEY, () -> new ConfiguredFeature<>(BIRD_NEST.get(), new NestConfiguration(IncubationBlocks.TWIG_TURKEY_NEST.get().defaultBlockState(), IncubationConstants.TURKEY)));
		}

		public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
			return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Incubation.MOD_ID, name));
		}

		public static void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, Supplier<? extends ConfiguredFeature<?, ?>> configuredFeature) {
			context.register(key, configuredFeature.get());
		}

	}

	public static final class IncubationPlacedFeatures {
		public static final ResourceKey<PlacedFeature> NEST_CHICKEN = createKey("nest_chicken");
		public static final ResourceKey<PlacedFeature> NEST_DUCK = createKey("nest_duck");
		public static final ResourceKey<PlacedFeature> NEST_TURKEY = createKey("nest_turkey");

		public static void bootstrap(BootstapContext<PlacedFeature> context) {
			register(context, NEST_CHICKEN, IncubationConfiguredFeatures.NEST_CHICKEN, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
			register(context, NEST_DUCK, IncubationConfiguredFeatures.NEST_DUCK, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
			register(context, NEST_TURKEY, IncubationConfiguredFeatures.NEST_TURKEY, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		}

		public static ResourceKey<PlacedFeature> createKey(String name) {
			return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Incubation.MOD_ID, name));
		}

		public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureHolder, PlacementModifier... modifiers) {
			context.register(key, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configuredFeatureHolder), List.of(modifiers)));
		}
	}
}
