package com.teamabnormals.incubation.core.data.server.tags;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.tags.IncubationBlockTags;
import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class IncubationBlockTagsProvider extends BlockTagsProvider {

	public IncubationBlockTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Incubation.MOD_ID, helper);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(BlockTags.MINEABLE_WITH_AXE).add(IncubationBlocks.CHICKEN_EGG_CRATE.get(), IncubationBlocks.TURTLE_EGG_CRATE.get());
		this.tag(BlockTags.MINEABLE_WITH_HOE).addTag(IncubationBlockTags.BIRD_NESTS);

		this.tag(IncubationBlockTags.BIRD_NESTS).addTag(IncubationBlockTags.TWIG_NESTS).addTag(IncubationBlockTags.HAY_NESTS);
		this.tag(IncubationBlockTags.TWIG_NESTS).add(IncubationBlocks.TWIG_NEST.get(), IncubationBlocks.TWIG_CHICKEN_NEST.get(), IncubationBlocks.TWIG_DUCK_NEST.get(), IncubationBlocks.TWIG_TURKEY_NEST.get());
		this.tag(IncubationBlockTags.HAY_NESTS).add(IncubationBlocks.HAY_NEST.get(), IncubationBlocks.HAY_CHICKEN_NEST.get(), IncubationBlocks.HAY_DUCK_NEST.get(), IncubationBlocks.HAY_TURKEY_NEST.get());
	}
}