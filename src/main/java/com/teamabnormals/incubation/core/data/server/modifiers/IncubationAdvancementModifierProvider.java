package com.teamabnormals.incubation.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.advancement.modification.AdvancementModifierProvider;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.CriteriaModifier;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class IncubationAdvancementModifierProvider extends AdvancementModifierProvider {
	private static final Item[] EDIBLE_ITEMS = new Item[]{IncubationItems.FRIED_EGG.get(), IncubationItems.SCRAMBLED_EGGS.get()};

	public IncubationAdvancementModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Incubation.MOD_ID, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		CriteriaModifier.Builder balancedDiet = CriteriaModifier.builder(this.modId);
		for (Item item : EDIBLE_ITEMS) {
			balancedDiet.addCriterion(BuiltInRegistries.ITEM.getKey(item).getPath(), ConsumeItemTrigger.TriggerInstance.usedItem(item));
		}
		this.entry("husbandry/balanced_diet").selects("husbandry/balanced_diet").addModifier(balancedDiet.requirements(Strategy.AND).build());
	}
}