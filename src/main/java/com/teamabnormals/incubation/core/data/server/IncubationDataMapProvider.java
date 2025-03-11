package com.teamabnormals.incubation.core.data.server;

import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class IncubationDataMapProvider extends DataMapProvider {

	public IncubationDataMapProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider);
	}

	@Override
	protected void gather(Provider provider) {
		this.builder(NeoForgeDataMaps.COMPOSTABLES)
				.add(IncubationBlocks.TWIG_NEST.getId(), new Compostable(0.65F), false)
				.add(IncubationBlocks.HAY_NEST.getId(), new Compostable(0.65F), false);
	}
}