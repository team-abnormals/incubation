package com.teamabnormals.incubation.common.block.entity;

import com.teamabnormals.incubation.common.block.BirdNestBlock;
import com.teamabnormals.incubation.core.registry.IncubationBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.VanillaHopperItemHandler;

import javax.annotation.Nonnull;

public class BirdNestBlockEntity extends BlockEntity {

	public BirdNestBlockEntity(BlockPos pos, BlockState state) {
		super(IncubationBlockEntityTypes.BIRD_NEST.get(), pos, state);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, BirdNestBlockEntity blockEntity) {
		int i = state.getValue(BirdNestBlock.EGGS);
		BlockPos blockpos = pos.below();
		BirdNestBlock block = (BirdNestBlock) state.getBlock();
		if (level.getBlockState(blockpos).hasBlockEntity()) {
			BlockEntity blockEntityBelow = level.getBlockEntity(blockpos);
			if (blockEntityBelow instanceof HopperBlockEntity) {
				if (!((HopperBlockEntity) blockEntityBelow).isOnCooldown() && insertEggToHopper(blockEntityBelow, new ItemStack(block.getEgg()))) {
					if (i > 1)
						level.setBlock(pos, state.setValue(BirdNestBlock.EGGS, i - 1), 2);
					else
						level.setBlock(pos, block.getEmptyNest().defaultBlockState(), 2);
				}
			}
		}
	}

	private static boolean insertEggToHopper(BlockEntity blockEntity, @Nonnull ItemStack stack) {
		HopperBlockEntity hopper = (HopperBlockEntity) blockEntity;
		VanillaHopperItemHandler inventory = new VanillaHopperItemHandler(hopper);

		if (!stack.isEmpty()) {
			stack = ItemHandlerHelper.insertItemStacked(inventory, stack, false);
		}

		if (!stack.isEmpty()) {
			return false;
		}

		if (!hopper.isOnCustomCooldown()) {
			hopper.setCooldown(8);
		}

		return true;
	}
}