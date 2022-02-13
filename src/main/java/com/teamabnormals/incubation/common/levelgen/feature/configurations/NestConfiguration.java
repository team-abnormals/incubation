package com.teamabnormals.incubation.common.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class NestConfiguration implements FeatureConfiguration {
	public static final Codec<NestConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockState.CODEC.fieldOf("state").forGetter((config) -> config.state), ResourceLocation.CODEC.fieldOf("entity_id").forGetter((config) -> config.entityType)).apply(instance, NestConfiguration::new));
	public final BlockState state;
	public final ResourceLocation entityType;

	public NestConfiguration(BlockState state, ResourceLocation entityType) {
		this.state = state;
		this.entityType = entityType;
	}
}