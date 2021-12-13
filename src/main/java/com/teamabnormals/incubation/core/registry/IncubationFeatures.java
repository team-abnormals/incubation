package com.teamabnormals.incubation.core.registry;

import com.teamabnormals.incubation.common.world.gen.feature.BirdNestFeature;
import com.teamabnormals.incubation.core.Incubation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(modid = Incubation.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IncubationFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Incubation.MOD_ID);

	public static final RegistryObject<Feature<BlockStateConfiguration>> BIRD_NEST = FEATURES.register("bird_nest", () -> new BirdNestFeature(BlockStateConfiguration.CODEC));

	public static final class IncubationConfiguredFeatures {
		public static final ConfiguredFeature<?, ?> NEST_CHICKEN = register("nest_chicken", BIRD_NEST.get().configured(new BlockStateConfiguration(IncubationBlocks.TWIG_CHICKEN_NEST.get().defaultBlockState())));
		public static final ConfiguredFeature<?, ?> NEST_DUCK = register("nest_duck", BIRD_NEST.get().configured(new BlockStateConfiguration(IncubationBlocks.TWIG_DUCK_NEST.get().defaultBlockState())));
		public static final ConfiguredFeature<?, ?> NEST_TURKEY = register("nest_turkey", BIRD_NEST.get().configured(new BlockStateConfiguration(IncubationBlocks.TWIG_TURKEY_NEST.get().defaultBlockState())));

		private static ConfiguredFeature<?, ?> register(String name, ConfiguredFeature<?, ?> configuredFeature) {
			return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Incubation.MOD_ID, name), configuredFeature);
		}
	}

	public static final class IncubationPlacedFeatures {
		public static final PlacedFeature NEST_CHICKEN = register("nest_chicken", IncubationConfiguredFeatures.NEST_CHICKEN.placed(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
		public static final PlacedFeature NEST_DUCK = register("nest_duck", IncubationConfiguredFeatures.NEST_DUCK.placed(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
		public static final PlacedFeature NEST_TURKEY = register("nest_turkey", IncubationConfiguredFeatures.NEST_TURKEY.placed(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

		private static PlacedFeature register(String name, PlacedFeature placedFeature) {
			return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Incubation.MOD_ID, name), placedFeature);
		}
	}
}
