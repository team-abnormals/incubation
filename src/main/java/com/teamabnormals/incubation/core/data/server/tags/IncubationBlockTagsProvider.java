package com.teamabnormals.incubation.core.data.server.tags;

import com.teamabnormals.incubation.core.Incubation;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class IncubationBlockTagsProvider extends BlockTagsProvider {

	public IncubationBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Incubation.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
	}
}