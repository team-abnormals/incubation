package com.teamabnormals.incubation.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class IncubationItemTags {
	public static final Tag.Named<Item> EGGS = TagUtil.forgeItemTag("eggs");
	public static final Tag.Named<Item> MILK = TagUtil.forgeItemTag("milk");
}
