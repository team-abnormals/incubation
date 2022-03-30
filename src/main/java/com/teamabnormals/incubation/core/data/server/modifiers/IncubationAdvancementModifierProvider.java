package com.teamabnormals.incubation.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.advancement.modification.AdvancementModifierProvider;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.CriteriaModifier;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;

public class IncubationAdvancementModifierProvider extends AdvancementModifierProvider {

	public IncubationAdvancementModifierProvider(DataGenerator dataGenerator) {
		super(dataGenerator, Incubation.MOD_ID);
	}

	@Override
	protected void registerEntries() {
		CriteriaModifier.Builder balancedDiet = CriteriaModifier.builder(this.modId);
		Collection<RegistryObject<Item>> items = IncubationItems.HELPER.getDeferredRegister().getEntries();
		items.forEach(item -> {
			if (item.get().isEdible()) {
				balancedDiet.addCriterion(item.get().getRegistryName().getPath(), ConsumeItemTrigger.TriggerInstance.usedItem(item.get()));
			}
		});
		this.entry("husbandry/balanced_diet").selects("husbandry/balanced_diet").addModifier(balancedDiet.requirements(RequirementsStrategy.AND).build());
	}
}