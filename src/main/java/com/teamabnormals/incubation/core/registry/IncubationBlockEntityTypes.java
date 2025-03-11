package com.teamabnormals.incubation.core.registry;

import com.teamabnormals.blueprint.core.util.registry.BlockEntitySubRegistryHelper;
import com.teamabnormals.incubation.common.block.BirdNestBlock;
import com.teamabnormals.incubation.common.block.entity.BirdNestBlockEntity;
import com.teamabnormals.incubation.core.Incubation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;

public class IncubationBlockEntityTypes {
	public static final BlockEntitySubRegistryHelper HELPER = Incubation.REGISTRY_HELPER.getBlockEntitySubHelper();

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BirdNestBlockEntity>> BIRD_NEST = HELPER.createBlockEntity("bird_nest", BirdNestBlockEntity::new, () -> Set.of(BlockEntitySubRegistryHelper.collectBlocks(BirdNestBlock.class)));
}