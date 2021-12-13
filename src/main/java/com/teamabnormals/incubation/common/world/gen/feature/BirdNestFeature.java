package com.teamabnormals.incubation.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.incubation.common.block.BirdNestBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class BirdNestFeature extends Feature<BlockStateConfiguration> {
	public BirdNestFeature(Codec<BlockStateConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
		BlockState blockstate = context.config().state.setValue(BirdNestBlock.EGGS, 2 + context.random().nextInt(3));
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();

		int i = level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
		BlockPos blockpos = new BlockPos(pos.getX(), i, pos.getZ());

		if (level.isEmptyBlock(blockpos) && level.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK) {
			level.setBlock(blockpos, blockstate, 2);
			return true;
		}

		return false;
	}
}