package com.teamabnormals.incubation.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.incubation.common.block.BirdNestBlock;
import com.teamabnormals.incubation.common.levelgen.feature.configurations.NestConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraftforge.registries.ForgeRegistries;

public class BirdNestFeature extends Feature<NestConfiguration> {
	public BirdNestFeature(Codec<NestConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NestConfiguration> context) {
		BlockState blockstate = context.config().state.setValue(BirdNestBlock.EGGS, 2 + context.random().nextInt(3));
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();

		int y = level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
		BlockPos blockpos = new BlockPos(pos.getX(), y, pos.getZ());

		if (level.isEmptyBlock(blockpos) && level.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK) {
			level.setBlock(blockpos, blockstate, 2);
			EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(context.config().entityType);
			if (entityType != null && entityType.create(level.getLevel()) instanceof Mob) {
				for (int i = 0; i < 4; ++i) {
					double posX = (double) pos.getX() + (random.nextDouble() - random.nextDouble()) * 4.5D;
					double posY = pos.getY() + random.nextInt(3) - 1;
					double posZ = (double) pos.getZ() + (random.nextDouble() - random.nextDouble()) * 4.5D;
					if (level.noCollision(entityType.getAABB(posX, posY, posZ))) {
						Mob entity = (Mob) entityType.create(level.getLevel());
						if (entity != null && SpawnPlacements.checkSpawnRules(entityType, level, MobSpawnType.STRUCTURE, BlockPos.containing(posX, posY, posZ), level.getRandom())) {
							entity.moveTo(posX, posY, posZ, random.nextFloat() * 360.0F, 0.0F);
							entity.finalizeSpawn(level, level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.STRUCTURE, null, null);
							entity.setBaby(random.nextInt(3) == 0);
							level.addFreshEntity(entity);
						}
					}
				}
			}

			return true;
		}

		return false;
	}
}