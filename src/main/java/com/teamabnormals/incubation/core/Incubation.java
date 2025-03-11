package com.teamabnormals.incubation.core;

import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.gallery.core.data.client.GalleryItemModelProvider;
import com.teamabnormals.incubation.core.data.client.IncubationBlockStateProvider;
import com.teamabnormals.incubation.core.data.client.IncubationItemModelProvider;
import com.teamabnormals.incubation.core.data.client.IncubationLanguageProvider;
import com.teamabnormals.incubation.core.data.server.IncubationDataMapProvider;
import com.teamabnormals.incubation.core.data.server.IncubationDatapackBuiltinEntriesProvider;
import com.teamabnormals.incubation.core.data.server.IncubationLootTableProvider;
import com.teamabnormals.incubation.core.data.server.IncubationRecipeProvider;
import com.teamabnormals.incubation.core.data.server.modifiers.IncubationAdvancementModifierProvider;
import com.teamabnormals.incubation.core.data.server.tags.IncubationBiomeTagsProvider;
import com.teamabnormals.incubation.core.data.server.tags.IncubationBlockTagsProvider;
import com.teamabnormals.incubation.core.data.server.tags.IncubationItemTagsProvider;
import com.teamabnormals.incubation.core.other.IncubationCompat;
import com.teamabnormals.incubation.core.other.tags.IncubationPaintingVariantTagsProvider;
import com.teamabnormals.incubation.core.registry.IncubationFeatures;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod(Incubation.MOD_ID)
public class Incubation {
	public static final String MOD_ID = "incubation";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public Incubation(IEventBus bus, ModContainer container) {
		REGISTRY_HELPER.register(bus);
		IncubationFeatures.FEATURES.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::dataSetup);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			IncubationItems.setupTabEditors();
		}
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			IncubationCompat.registerCompat();
		});
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<Provider> provider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		boolean server = event.includeServer();
		IncubationDatapackBuiltinEntriesProvider datapack = new IncubationDatapackBuiltinEntriesProvider(output, provider);
		generator.addProvider(server, datapack);
		provider = datapack.getRegistryProvider();

		IncubationBlockTagsProvider blockTags = new IncubationBlockTagsProvider(output, provider, helper);
		generator.addProvider(server, blockTags);
		generator.addProvider(server, new IncubationItemTagsProvider(output, provider, blockTags.contentsGetter(), helper));
		generator.addProvider(server, new IncubationBiomeTagsProvider(output, provider, helper));
		generator.addProvider(server, new IncubationPaintingVariantTagsProvider(output, provider, helper));
		generator.addProvider(server, new IncubationRecipeProvider(output, provider));
		generator.addProvider(server, new IncubationLootTableProvider(output, provider));
		generator.addProvider(server, new IncubationAdvancementModifierProvider(output, provider));
		generator.addProvider(server, new IncubationDataMapProvider(output, provider));

		boolean client = event.includeClient();
		generator.addProvider(client, new IncubationItemModelProvider(output, helper));
		generator.addProvider(client, new IncubationBlockStateProvider(output, helper));
		generator.addProvider(client, new IncubationLanguageProvider(output));

		generator.addProvider(client, new GalleryItemModelProvider(MOD_ID, output, helper, provider));
	}

	public static ResourceLocation location(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}