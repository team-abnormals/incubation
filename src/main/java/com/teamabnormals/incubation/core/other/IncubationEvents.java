package com.teamabnormals.incubation.core.other;

import com.teamabnormals.blueprint.core.api.EggLayer;
import com.teamabnormals.incubation.common.entity.ai.goal.LayEggInNestGoal;
import com.teamabnormals.incubation.core.Incubation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

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
}
