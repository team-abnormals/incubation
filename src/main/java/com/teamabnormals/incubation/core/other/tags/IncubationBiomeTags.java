package com.teamabnormals.incubation.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.incubation.core.Incubation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class IncubationBiomeTags {
	public static final TagKey<Biome> HAS_CHICKEN_NEST = biomeTag("has_feature/chicken_nest");
	public static final TagKey<Biome> HAS_DUCK_NEST = biomeTag("has_feature/duck_nest");
	public static final TagKey<Biome> HAS_TURKEY_NEST = biomeTag("has_feature/turkey_nest");

	private static TagKey<Biome> biomeTag(String name) {
		return TagUtil.biomeTag(Incubation.MOD_ID, name);
	}
}
