package com.teamabnormals.incubation.core.data.server.tags;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.IncubationConstants;
import com.teamabnormals.incubation.core.other.tags.IncubationBiomeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

public class IncubationBiomeTagsProvider extends BiomeTagsProvider {

	public IncubationBiomeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Incubation.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(IncubationBiomeTags.HAS_CHICKEN_NEST).add(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.DARK_FOREST).addTag(BiomeTags.IS_JUNGLE).addTag(BiomeTags.IS_TAIGA);
		this.tag(IncubationBiomeTags.HAS_DUCK_NEST).addOptionalTag(IncubationConstants.HAS_DUCK);
		this.tag(IncubationBiomeTags.HAS_TURKEY_NEST).addOptionalTag(IncubationConstants.HAS_TURKEY);
	}
}