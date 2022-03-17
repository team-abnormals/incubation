package com.teamabnormals.incubation.core.other;

import com.teamabnormals.incubation.api.EggLayer;
import com.teamabnormals.incubation.common.entity.ai.goal.LayEggInNestGoal;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.tags.IncubationBiomeTags;
import com.teamabnormals.incubation.core.registry.IncubationFeatures.IncubationPlacedFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = Incubation.MOD_ID)
public class IncubationEvents {

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof EggLayer && entity instanceof Animal animal) {
			if (animal.goalSelector.getAvailableGoals().stream().noneMatch((goal) -> goal.getGoal() instanceof LayEggInNestGoal)) {
				animal.goalSelector.addGoal(3, new LayEggInNestGoal(animal, 1.0D));
			}
		}
	}

	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation name = event.getName();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		Biome biome = ForgeRegistries.BIOMES.getValue(name);

		if (isTagged(biome, IncubationBiomeTags.HAS_CHICKEN_NEST)) {
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IncubationPlacedFeatures.NEST_CHICKEN);
		}

		if (ModList.get().isLoaded(IncubationConstants.ENVIRONMENTAL) && isTagged(biome, IncubationBiomeTags.HAS_DUCK_NEST)) {
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IncubationPlacedFeatures.NEST_DUCK);
		}

		if (ModList.get().isLoaded(IncubationConstants.AUTUMNITY) && isTagged(biome, IncubationBiomeTags.HAS_TURKEY_NEST)) {
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IncubationPlacedFeatures.NEST_TURKEY);
		}
	}

	private static boolean isTagged(Biome biome, TagKey<Biome> tagKey) {
		return ForgeRegistries.BIOMES.tags().getTag(tagKey).contains(biome);
	}
}
