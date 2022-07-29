package com.teamabnormals.incubation.core.data.client;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class IncubationItemModelProvider extends ItemModelProvider {

	public IncubationItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Incubation.MOD_ID, existingFileHelper);
	}

	@Override
	public void registerModels() {
		this.generated(IncubationItems.FRIED_EGG.get());
		this.generated(IncubationItems.SCRAMBLED_EGGS.get());
	}

	private void generated(ItemLike item) {
		ResourceLocation itemName = ForgeRegistries.ITEMS.getKey(item.asItem());
		withExistingParent(itemName.getPath(), "item/generated").texture("layer0", new ResourceLocation(this.modid, "item/" + itemName.getPath()));
	}
}