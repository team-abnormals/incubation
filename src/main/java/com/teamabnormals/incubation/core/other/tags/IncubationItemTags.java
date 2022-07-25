package com.teamabnormals.incubation.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.incubation.core.Incubation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class IncubationItemTags {
	public static final TagKey<Item> COOKED_EGGS = TagUtil.itemTag("forge", "cooked_eggs");

	private static TagKey<Item> itemTag(String name) {
		return TagUtil.itemTag(Incubation.MOD_ID, name);
	}
}
