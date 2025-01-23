package com.teamabnormals.incubation.core;

import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.gallery.core.data.client.GalleryAssetsRemolderProvider;
import com.teamabnormals.gallery.core.data.client.GalleryItemModelProvider;
import com.teamabnormals.incubation.core.data.client.IncubationBlockStateProvider;
import com.teamabnormals.incubation.core.data.client.IncubationItemModelProvider;
import com.teamabnormals.incubation.core.data.client.IncubationLanguageProvider;
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
import com.teamabnormals.incubation.core.registry.IncubationPaintingVariants;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

@Mod(Incubation.MOD_ID)
public class Incubation {
	public static final String MOD_ID = "incubation";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public Incubation() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);
		IncubationFeatures.FEATURES.register(bus);
		IncubationPaintingVariants.PAINTING_VARIANTS.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::dataSetup);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			IncubationItems.setupTabEditors();
		});
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
		IncubationBlockTagsProvider blockTags = new IncubationBlockTagsProvider(output, provider, helper);
		generator.addProvider(server, blockTags);
		generator.addProvider(server, new IncubationItemTagsProvider(output, provider, blockTags.contentsGetter(), helper));
		generator.addProvider(server, new IncubationBiomeTagsProvider(output, provider, helper));
		generator.addProvider(server, new IncubationPaintingVariantTagsProvider(output, provider, helper));
		generator.addProvider(server, new IncubationRecipeProvider(output));
		generator.addProvider(server, new IncubationLootTableProvider(output));
		generator.addProvider(server, new IncubationAdvancementModifierProvider(output, provider));
		generator.addProvider(server, new IncubationDatapackBuiltinEntriesProvider(output, provider));

		boolean client = event.includeClient();
		generator.addProvider(client, new IncubationItemModelProvider(output, helper));
		generator.addProvider(client, new IncubationBlockStateProvider(output, helper));
		generator.addProvider(client, new IncubationLanguageProvider(output));

		generator.addProvider(client, new GalleryItemModelProvider(MOD_ID, output, helper));
		generator.addProvider(client, new GalleryAssetsRemolderProvider(MOD_ID, output, provider));
	}
}