package com.teamabnormals.incubation.common.entity.ai.goal;

import com.teamabnormals.incubation.common.block.BirdNestBlock;
import com.teamabnormals.incubation.common.block.EmptyNestBlock;
import com.teamabnormals.incubation.core.api.api.EggLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class LayEggInNestGoal extends MoveToBlockGoal {
	private final Animal bird;
	private final EggLayer eggLayer;
	private int eggCounter;

	public LayEggInNestGoal(Animal birdIn, double speedIn) {
		super(birdIn, speedIn, 16);
		this.bird = birdIn;
		this.eggLayer = (EggLayer) (Animal) birdIn;
	}

	public boolean canUse() {
		return this.canEggBeLaid() && super.canUse();
	}

	public boolean canContinueToUse() {
		return this.canEggBeLaid() && super.canContinueToUse();
	}

	protected int nextStartTick(PathfinderMob creatureIn) {
		return 40;
	}

	public void start() {
		super.start();
		this.eggCounter = this.adjustedTickDelay(30);
	}

	public void tick() {
		super.tick();
		if (this.isReachedTarget() && this.canEggBeLaid()) {
			this.eggCounter = Math.max(0, this.eggCounter - 1);
			if (this.eggCounter <= 0) {
				BlockPos blockpos = this.blockPos.above();
				BlockState blockstate = this.bird.level.getBlockState(blockpos);
				Block block = blockstate.getBlock();

				if (block instanceof EmptyNestBlock) {
					this.bird.level.setBlock(blockpos, ((EmptyNestBlock) block).getNest(this.eggLayer.getEggItem()).defaultBlockState(), 3);
					this.resetBird();
				} else if (block instanceof BirdNestBlock && ((BirdNestBlock) block).getEgg() == this.eggLayer.getEggItem()) {
					int i = blockstate.getValue(BirdNestBlock.EGGS);
					if (i < 6) {
						this.bird.level.setBlock(blockpos, blockstate.setValue(BirdNestBlock.EGGS, i + 1), 3);
						this.resetBird();
					}
				}
			}
		}
	}
	   
	private boolean canEggBeLaid() {
		return !this.bird.isBaby() && !this.eggLayer.isBirdJockey() && this.eggLayer.getEggTimer() < 400;
	}

	private void resetBird() {
		Random random = this.bird.getRandom();
		this.bird.playSound(this.eggLayer.getEggLayingSound(), 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
		this.eggLayer.setEggTimer(this.eggLayer.getNextEggTime(random));
	}

	protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
		BlockState blockstate = worldIn.getBlockState(pos.above());
		Block block = blockstate.getBlock();

		return block instanceof EmptyNestBlock || (block instanceof BirdNestBlock && ((BirdNestBlock) block).getEgg() == this.eggLayer.getEggItem() && blockstate.getValue(BirdNestBlock.EGGS) < 6);
	}
}