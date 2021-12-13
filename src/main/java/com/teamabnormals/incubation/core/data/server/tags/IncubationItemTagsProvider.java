package com.teamabnormals.incubation.core.data.server.tags;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.tags.IncubationItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

public class IncubationItemTagsProvider extends ItemTagsProvider {

	public IncubationItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(generator, blockTagsProvider, Incubation.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(IncubationItemTags.EGGS).add(Items.EGG);
		this.tag(IncubationItemTags.MILK).add(Items.MILK_BUCKET);
	}
}