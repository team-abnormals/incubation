package com.teamabnormals.incubation.core.data.server.tags;

import com.teamabnormals.blueprint.core.data.server.tags.BlueprintBiomeTagsProvider;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.IncubationConstants;
import com.teamabnormals.incubation.core.other.tags.IncubationBiomeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class IncubationBiomeTagsProvider extends BlueprintBiomeTagsProvider {

	public IncubationBiomeTagsProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(Incubation.MOD_ID, generator, fileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(IncubationBiomeTags.HAS_CHICKEN_NEST).addTag(BiomeTags.IS_FOREST);
		this.tag(IncubationBiomeTags.HAS_DUCK_NEST).addOptionalTag(IncubationConstants.HAS_DUCK);
		this.tag(IncubationBiomeTags.HAS_TURKEY_NEST).addOptionalTag(IncubationConstants.HAS_TURKEY);
	}
}