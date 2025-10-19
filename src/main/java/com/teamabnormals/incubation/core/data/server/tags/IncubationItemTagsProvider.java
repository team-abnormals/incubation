package com.teamabnormals.incubation.core.data.server.tags;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.tags.IncubationBlockTags;
import com.teamabnormals.incubation.core.other.tags.IncubationItemTags;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class IncubationItemTagsProvider extends ItemTagsProvider {

	public IncubationItemTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, CompletableFuture<TagLookup<Block>> tagLookup, ExistingFileHelper fileHelper) {
		super(output, lookupProvider, tagLookup, Incubation.MOD_ID, fileHelper);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(Tags.Items.FOODS).addTag(IncubationItemTags.FOODS_COOKED_EGG).add(IncubationItems.SCRAMBLED_EGGS.get());
		this.tag(IncubationItemTags.FOODS_COOKED_EGG).add(IncubationItems.FRIED_EGG.get());

		this.copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
		this.copy(IncubationBlockTags.STORAGE_BLOCKS_EGG, IncubationItemTags.STORAGE_BLOCKS_EGG);
		this.copy(IncubationBlockTags.STORAGE_BLOCKS_TURTLE_EGG, IncubationItemTags.STORAGE_BLOCKS_TURTLE_EGG);
	}
}