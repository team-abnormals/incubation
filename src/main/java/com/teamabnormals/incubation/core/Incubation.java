package com.teamabnormals.incubation.core;

import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.gallery.core.data.client.GalleryItemModelProvider;
import com.teamabnormals.incubation.common.block.BirdNestBlock;
import com.teamabnormals.incubation.core.data.client.IncubationBlockStateProvider;
import com.teamabnormals.incubation.core.data.client.IncubationItemModelProvider;
import com.teamabnormals.incubation.core.data.client.IncubationLanguageProvider;
import com.teamabnormals.incubation.core.data.server.IncubationDataMapProvider;
import com.teamabnormals.incubation.core.data.server.IncubationDatapackProvider;
import com.teamabnormals.incubation.core.data.server.IncubationLootTableProvider;
import com.teamabnormals.incubation.core.data.server.IncubationRecipeProvider;
import com.teamabnormals.incubation.core.data.server.modifiers.IncubationAdvancementModifierProvider;
import com.teamabnormals.incubation.core.data.server.tags.IncubationBiomeTagsProvider;
import com.teamabnormals.incubation.core.data.server.tags.IncubationBlockTagsProvider;
import com.teamabnormals.incubation.core.data.server.tags.IncubationItemTagsProvider;
import com.teamabnormals.incubation.core.other.IncubationCompat;
import com.teamabnormals.incubation.core.other.tags.IncubationPaintingVariantTagsProvider;
import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import com.teamabnormals.incubation.core.registry.IncubationFeatures;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.WorldlyContainerHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.items.wrapper.ForwardingItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.concurrent.CompletableFuture;

@Mod(Incubation.MOD_ID)
public class Incubation {
	public static final String MOD_ID = "incubation";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public Incubation(IEventBus bus) {
		IncubationBlocks.BLOCKS.register(bus);
		IncubationItems.ITEMS.register(bus);
		IncubationFeatures.FEATURES.register(bus);

		bus.addListener(this::registerCapabilities);

		bus.addListener(this::commonSetup);
		bus.addListener(this::dataSetup);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			IncubationItems.setupTabEditors();
		}
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(IncubationCompat::register);
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<Provider> provider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		boolean server = event.includeServer();
		IncubationDatapackProvider datapack = new IncubationDatapackProvider(output, provider);
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

	private void registerCapabilities(RegisterCapabilitiesEvent event) {
		IncubationBlocks.BLOCKS.getDeferredRegister().getEntries().stream().map(DeferredHolder::get).filter(block -> block instanceof BirdNestBlock).forEach(block -> {
			WorldlyContainerHolder birdNest = (WorldlyContainerHolder) block;
			event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, side) -> {
				if (side == null) {
					return new ForwardingItemHandler(() -> new InvWrapper(birdNest.getContainer(level.getBlockState(pos), level, pos)));
				} else {
					return new ForwardingItemHandler(() -> new SidedInvWrapper(birdNest.getContainer(level.getBlockState(pos), level, pos), side));
				}
			}, block);
		});
	}

	public static ResourceLocation location(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}