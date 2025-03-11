package com.teamabnormals.incubation.core.data.client;

import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IncubationItemModelProvider extends BlueprintItemModelProvider {

	public IncubationItemModelProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Incubation.MOD_ID, helper);
	}

	@Override
	public void registerModels() {
		this.generatedItem(IncubationItems.FRIED_EGG, IncubationItems.SCRAMBLED_EGGS);
	}
}