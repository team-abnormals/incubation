package com.teamabnormals.incubation.core;

import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.incubation.core.data.client.IncubationBlockStateProvider;
import com.teamabnormals.incubation.core.data.client.IncubationItemModelProvider;
import com.teamabnormals.incubation.core.data.client.IncubationLanguageProvider;
import com.teamabnormals.incubation.core.data.server.IncubationLootTableProvider;
import com.teamabnormals.incubation.core.data.server.IncubationRecipeProvider;
import com.teamabnormals.incubation.core.data.server.tags.IncubationBiomeTagsProvider;
import com.teamabnormals.incubation.core.data.server.tags.IncubationBlockTagsProvider;
import com.teamabnormals.incubation.core.other.IncubationCompat;
import com.teamabnormals.incubation.core.registry.IncubationFeatures;
import com.teamabnormals.incubation.core.registry.IncubationStructures;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(Incubation.MOD_ID)
public class Incubation {
	public static final String MOD_ID = "incubation";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public Incubation() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		REGISTRY_HELPER.register(bus);
		IncubationFeatures.FEATURES.register(bus);
		MinecraftForge.EVENT_BUS.register(this);

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
		ExistingFileHelper fileHelper = event.getExistingFileHelper();

		if (event.includeServer()) {
			IncubationBlockTagsProvider blockTags = new IncubationBlockTagsProvider(generator, fileHelper);
			generator.addProvider(blockTags);
			generator.addProvider(new IncubationBiomeTagsProvider(generator, fileHelper));
			generator.addProvider(new IncubationRecipeProvider(generator));
			generator.addProvider(new IncubationLootTableProvider(generator));
		}

		if (event.includeClient()) {
			generator.addProvider(new IncubationItemModelProvider(generator, fileHelper));
			generator.addProvider(new IncubationBlockStateProvider(generator, fileHelper));
			generator.addProvider(new IncubationLanguageProvider(generator));
		}
	}
}