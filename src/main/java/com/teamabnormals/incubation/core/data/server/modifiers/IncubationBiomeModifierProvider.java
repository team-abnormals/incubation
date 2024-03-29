package com.teamabnormals.incubation.core.data.server.modifiers;

import com.mojang.serialization.JsonOps;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.tags.IncubationBiomeTags;
import com.teamabnormals.incubation.core.registry.IncubationFeatures.IncubationPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IncubationBiomeModifierProvider {

	public static JsonCodecProvider<BiomeModifier> create(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		RegistryAccess access = RegistryAccess.builtinCopy();
		Registry<Biome> biomeRegistry = access.registryOrThrow(Registry.BIOME_REGISTRY);
		Registry<PlacedFeature> placedFeatures = access.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
		HashMap<ResourceLocation, BiomeModifier> modifiers = new HashMap<>();

		addModifier(modifiers, "add_feature/chicken_nest", new AddFeaturesBiomeModifier(tag(biomeRegistry, IncubationBiomeTags.HAS_CHICKEN_NEST), of(placedFeatures, IncubationPlacedFeatures.NEST_CHICKEN), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/duck_nest", new AddFeaturesBiomeModifier(tag(biomeRegistry, IncubationBiomeTags.HAS_DUCK_NEST), of(placedFeatures, IncubationPlacedFeatures.NEST_DUCK), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/turkey_nest", new AddFeaturesBiomeModifier(tag(biomeRegistry, IncubationBiomeTags.HAS_TURKEY_NEST), of(placedFeatures, IncubationPlacedFeatures.NEST_TURKEY), GenerationStep.Decoration.VEGETAL_DECORATION));

		return JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, Incubation.MOD_ID, RegistryOps.create(JsonOps.INSTANCE, access), ForgeRegistries.Keys.BIOME_MODIFIERS, modifiers);
	}

	private static HolderSet<Biome> tag(Registry<Biome> biomeRegistry, TagKey<Biome> tagKey) {
		return new HolderSet.Named<>(biomeRegistry, tagKey);
	}

	private static void addModifier(HashMap<ResourceLocation, BiomeModifier> modifiers, String name, BiomeModifier modifier) {
		modifiers.put(new ResourceLocation(Incubation.MOD_ID, name), modifier);
	}

	@SafeVarargs
	@SuppressWarnings("ConstantConditions")
	private static HolderSet<PlacedFeature> of(Registry<PlacedFeature> placedFeatures, RegistryObject<PlacedFeature>... features) {
		return HolderSet.direct(Stream.of(features).map(registryObject -> placedFeatures.getOrCreateHolderOrThrow(registryObject.getKey())).collect(Collectors.toList()));
	}
}