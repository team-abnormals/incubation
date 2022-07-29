package com.teamabnormals.incubation.core.data.server.tags;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.IncubationConstants;
import com.teamabnormals.incubation.core.other.tags.IncubationBiomeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class IncubationBiomeTagsProvider extends BiomeTagsProvider {

	public IncubationBiomeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Incubation.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(IncubationBiomeTags.HAS_CHICKEN_NEST).addTag(BiomeTags.IS_FOREST);
		this.tag(IncubationBiomeTags.HAS_DUCK_NEST).addOptionalTag(IncubationConstants.HAS_DUCK);
		this.tag(IncubationBiomeTags.HAS_TURKEY_NEST).addOptionalTag(IncubationConstants.HAS_TURKEY);
	}
}