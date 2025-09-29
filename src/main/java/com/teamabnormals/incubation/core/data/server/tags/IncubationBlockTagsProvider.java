package com.teamabnormals.incubation.core.data.server.tags;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.tags.IncubationBlockTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.incubation.core.registry.IncubationBlocks.*;

public class IncubationBlockTagsProvider extends BlockTagsProvider {

	public IncubationBlockTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Incubation.MOD_ID, helper);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(BlockTags.MINEABLE_WITH_AXE).add(CHICKEN_EGG_CRATE.get(), TURTLE_EGG_CRATE.get());
		this.tag(BlockTags.MINEABLE_WITH_HOE).addTag(IncubationBlockTags.BIRD_NESTS);

		this.tag(IncubationBlockTags.BIRD_NESTS).addTag(IncubationBlockTags.TWIG_NESTS).addTag(IncubationBlockTags.HAY_NESTS);
		this.tag(IncubationBlockTags.TWIG_NESTS).add(TWIG_NEST.get(), TWIG_CHICKEN_NEST.get(), TWIG_DUCK_NEST.get(), TWIG_TURKEY_NEST.get());
		this.tag(IncubationBlockTags.HAY_NESTS).add(HAY_NEST.get(), HAY_CHICKEN_NEST.get(), HAY_DUCK_NEST.get(), HAY_TURKEY_NEST.get());

		this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(IncubationBlockTags.STORAGE_BLOCKS_EGG).addTag(IncubationBlockTags.STORAGE_BLOCKS_TURTLE_EGG);
		this.tag(IncubationBlockTags.STORAGE_BLOCKS_EGG).add(CHICKEN_EGG_CRATE.get());
		this.tag(IncubationBlockTags.STORAGE_BLOCKS_TURTLE_EGG).add(TURTLE_EGG_CRATE.get());
	}
}