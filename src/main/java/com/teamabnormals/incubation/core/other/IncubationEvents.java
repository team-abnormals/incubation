package com.teamabnormals.incubation.core.other;

import com.teamabnormals.incubation.common.entity.ai.goal.LayEggInNestGoal;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.api.EggLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Incubation.MOD_ID)
public class IncubationEvents {

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof EggLayer && entity instanceof Animal animal) {
			if (animal.goalSelector.getAvailableGoals().stream().noneMatch((goal) -> goal.getGoal() instanceof LayEggInNestGoal)) {
				animal.goalSelector.addGoal(3, new LayEggInNestGoal(animal, 1.0D));
			}
		}
	}

//	@SubscribeEvent
//	public static void onBiomeLoad(BiomeLoadingEvent event) {
//		ResourceLocation name = event.getName();
//		BiomeCategory category = event.getCategory();
//		BiomeGenerationSettingsBuilder generation = event.getGeneration();
//
//		if (name == null) return;
//
//		if (category.equals(BiomeCategory.FOREST)) {
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IncubationPlacedFeatures.NEST_CHICKEN);
//		}
//
//		if (ModList.get().isLoaded(IncubationConstants.ENVIRONMENTAL) && !DataUtil.matchesKeys(name, Biomes.FROZEN_RIVER) && (category.equals(BiomeCategory.SWAMP) || category.equals(BiomeCategory.RIVER))) {
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IncubationPlacedFeatures.NEST_DUCK);
//		}
//
//		if (ModList.get().isLoaded(IncubationConstants.AUTUMNITY) && name.toString().contains("maple") || name.toString().contains("pumpkin_fields")) {
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IncubationPlacedFeatures.NEST_TURKEY);
//		}
//	}
}
