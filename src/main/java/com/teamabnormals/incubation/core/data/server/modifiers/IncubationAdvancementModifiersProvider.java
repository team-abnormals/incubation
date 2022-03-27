package com.teamabnormals.incubation.core.data.server.modifiers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.AdvancementModifier.Mode;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.CriteriaModifier;
import com.teamabnormals.blueprint.core.util.modification.AdvancementModifierProvider;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IncubationAdvancementModifiersProvider extends AdvancementModifierProvider {

	public IncubationAdvancementModifiersProvider(DataGenerator dataGenerator) {
		super(dataGenerator, Incubation.MOD_ID);
	}

	@Override
	protected void registerEntries() {
		this.registerEntry("husbandry/balanced_diet", createCriteriaModifier(Mode.MODIFY, Pair.of("fried_egg", new Criterion(ConsumeItemTrigger.TriggerInstance.usedItem(IncubationItems.FRIED_EGG.get()))), Pair.of("scrambled_eggs", new Criterion(ConsumeItemTrigger.TriggerInstance.usedItem(IncubationItems.SCRAMBLED_EGGS.get())))), new ResourceLocation("husbandry/balanced_diet"));
	}

	private Pair<Optional<Map<String, Criterion>>, Optional<String[]>> collectCriterions(List<Pair<String, Criterion>> criterions) {
		Optional<Map<String, Criterion>> criterionMap = Optional.of(Maps.newHashMap());
		ArrayList<String> reqs = Lists.newArrayList();

		criterions.forEach(pair -> {
			criterionMap.get().put(this.getModId() + ":" + pair.getFirst(), pair.getSecond());
			reqs.add(this.getModId() + ":" + pair.getFirst());
		});

		return Pair.of(criterionMap, Optional.of(reqs.toArray(String[]::new)));
	}

	private CriteriaModifier createCriteriaModifier(Mode mode, Pair<String, Criterion>... criterions) {
		Pair<Optional<Map<String, Criterion>>, Optional<String[]>> requirements = collectCriterions(Arrays.asList(criterions));
		ArrayList<String[]> reqs = Lists.newArrayList();
		for (String string : requirements.getSecond().get()) {
			reqs.add(new String[]{string});
		}

		return new CriteriaModifier(mode, requirements.getFirst(), Optional.of(reqs.toArray(String[][]::new)));
	}
}