package com.teamabnormals.incubation.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class IncubationItemTags {
	public static final TagKey<Item> FOODS_COOKED_EGG = TagUtil.itemTag("c", "foods/cooked_egg");

	public static final TagKey<Item> STORAGE_BLOCKS_EGG = TagUtil.itemTag("c", "storage_blocks/egg");
	public static final TagKey<Item> STORAGE_BLOCKS_TURTLE_EGG = TagUtil.itemTag("c", "storage_blocks/turtle_egg");
}
