package com.teamabnormals.incubation.core;

import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.incubation.core.data.client.IncubationBlockStateProvider;
import com.teamabnormals.incubation.core.data.client.IncubationItemModelProvider;
import com.teamabnormals.incubation.core.data.client.IncubationLanguageProvider;
import com.teamabnormals.incubation.core.data.server.IncubationLootTableProvider;
import com.teamabnormals.incubation.core.data.server.IncubationRecipeProvider;
import com.teamabnormals.incubation.core.data.server.modifiers.IncubationAdvancementModifierProvider;
import com.teamabnormals.incubation.core.data.server.modifiers.IncubationBiomeModifierProvider;
import com.teamabnormals.incubation.core.data.server.tags.IncubationBiomeTagsProvider;
import com.teamabnormals.incubation.core.data.server.tags.IncubationBlockTagsProvider;
import com.teamabnormals.incubation.core.data.server.tags.IncubationItemTagsProvider;
import com.teamabnormals.incubation.core.other.IncubationCompat;
import com.teamabnormals.incubation.core.registry.IncubationFeatures;
import com.teamabnormals.incubation.core.registry.IncubationFeatures.IncubationConfiguredFeatures;
import com.teamabnormals.incubation.core.registry.IncubationFeatures.IncubationPlacedFeatures;
import com.teamabnormals.incubation.core.registry.IncubationStructures;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Incubation.MOD_ID)
public class Incubation {
	public static final String MOD_ID = "incubation";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public Incubation() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);
		IncubationFeatures.FEATURES.register(bus);
		IncubationConfiguredFeatures.CONFIGURED_FEATURES.register(bus);
		IncubationPlacedFeatures.PLACED_FEATURES.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::dataSetup);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			IncubationCompat.registerCompat();
			IncubationStructures.setupVillagerHouses();
		});
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		boolean includeServer = event.includeServer();
		IncubationBlockTagsProvider blockTags = new IncubationBlockTagsProvider(generator, existingFileHelper);
		generator.addProvider(includeServer, blockTags);
		generator.addProvider(includeServer, new IncubationItemTagsProvider(generator, blockTags, existingFileHelper));
		generator.addProvider(includeServer, new IncubationBiomeTagsProvider(generator, existingFileHelper));
		generator.addProvider(includeServer, new IncubationRecipeProvider(generator));
		generator.addProvider(includeServer, new IncubationLootTableProvider(generator));
		generator.addProvider(includeServer, new IncubationAdvancementModifierProvider(generator));
		generator.addProvider(includeServer, IncubationBiomeModifierProvider.create(generator, existingFileHelper));

		boolean includeClient = event.includeClient();
		generator.addProvider(includeClient, new IncubationItemModelProvider(generator, existingFileHelper));
		generator.addProvider(includeClient, new IncubationBlockStateProvider(generator, existingFileHelper));
		generator.addProvider(includeClient, new IncubationLanguageProvider(generator));
	}
}