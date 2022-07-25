package com.teamabnormals.incubation.core.data.server.tags;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.tags.IncubationBlockTags;
import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class IncubationBlockTagsProvider extends BlockTagsProvider {

	public IncubationBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Incubation.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(BlockTags.MINEABLE_WITH_AXE).add(IncubationBlocks.CHICKEN_EGG_CRATE.get(), IncubationBlocks.TURTLE_EGG_CRATE.get());
		this.tag(BlockTags.MINEABLE_WITH_HOE).addTag(IncubationBlockTags.BIRD_NESTS);

		this.tag(IncubationBlockTags.BIRD_NESTS).addTag(IncubationBlockTags.TWIG_NESTS).addTag(IncubationBlockTags.HAY_NESTS);
		this.tag(IncubationBlockTags.TWIG_NESTS).add(IncubationBlocks.TWIG_NEST.get(), IncubationBlocks.TWIG_CHICKEN_NEST.get(), IncubationBlocks.TWIG_DUCK_NEST.get(), IncubationBlocks.TWIG_TURKEY_NEST.get());
		this.tag(IncubationBlockTags.HAY_NESTS).add(IncubationBlocks.HAY_NEST.get(), IncubationBlocks.HAY_CHICKEN_NEST.get(), IncubationBlocks.HAY_DUCK_NEST.get(), IncubationBlocks.HAY_TURKEY_NEST.get());
	}
}