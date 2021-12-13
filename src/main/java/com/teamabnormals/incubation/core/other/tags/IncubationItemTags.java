package com.teamabnormals.incubation.core.other.tags;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class IncubationItemTags {
	public static final Tag.Named<Item> EGGS = createForgeTag("eggs");
	public static final Tag.Named<Item> MILK = createForgeTag("milk");

	private static Tag.Named<Item> createForgeTag(String name) {
		return ItemTags.bind("forge:" + name);
	}
}
