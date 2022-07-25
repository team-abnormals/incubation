package com.teamabnormals.incubation.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.incubation.core.Incubation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class IncubationBlockTags {
	public static final TagKey<Block> BIRD_NESTS = blockTag("bird_nests");
	public static final TagKey<Block> TWIG_NESTS = blockTag("twig_nests");
	public static final TagKey<Block> HAY_NESTS = blockTag("hay_nests");

	private static TagKey<Block> blockTag(String name) {
		return TagUtil.blockTag(Incubation.MOD_ID, name);
	}
}