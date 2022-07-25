package com.teamabnormals.incubation.core.data.server.tags;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.tags.IncubationItemTags;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class IncubationItemTagsProvider extends ItemTagsProvider {

	public IncubationItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper existingFileHelper) {
		super(generator, blockTags, Incubation.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(IncubationItemTags.COOKED_EGGS).add(IncubationItems.FRIED_EGG.get());
	}
}