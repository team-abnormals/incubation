package com.teamabnormals.incubation.core.data.server;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.data.server.modifiers.IncubationBiomeModifierProvider;
import com.teamabnormals.incubation.core.registry.IncubationFeatures.IncubationConfiguredFeatures;
import com.teamabnormals.incubation.core.registry.IncubationFeatures.IncubationPlacedFeatures;
import com.teamabnormals.incubation.core.registry.datapack.IncubationPaintingVariants;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class IncubationDatapackProvider extends DatapackBuiltinEntriesProvider {

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.PAINTING_VARIANT, IncubationPaintingVariants::bootstrap)
			.add(Registries.CONFIGURED_FEATURE, IncubationConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, IncubationPlacedFeatures::bootstrap)
			.add(Keys.BIOME_MODIFIERS, IncubationBiomeModifierProvider::bootstrap);

	public IncubationDatapackProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider, BUILDER, Set.of(Incubation.MOD_ID));
	}
}