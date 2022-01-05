package com.teamabnormals.incubation.common.block;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.function.Supplier;

public class EmptyNestBlock extends Block {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
	private final Map<Supplier<? extends Item>, Block> NESTS = Maps.newHashMap();

	public EmptyNestBlock(Properties properties) {
		super(properties);
	}

	public void addNest(Supplier<? extends Item> egg, Block nest) {
		NESTS.put(egg, nest);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (player.mayBuild()) {
			ItemStack itemstack = player.getItemInHand(handIn);
			Item item = itemstack.getItem();
			Block nest = this.getNest(item);

			if (nest != null) {
				if (!player.getAbilities().instabuild && !worldIn.isClientSide) {
					itemstack.shrink(1);
				}
				worldIn.setBlock(pos, nest.defaultBlockState(), 3);

				return InteractionResult.sidedSuccess(worldIn.isClientSide);
			}

			return InteractionResult.CONSUME;
		} else {
			return super.use(state, worldIn, pos, player, handIn, hit);
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
	}

	public Block getNest(Item item) {
		if (item != Items.AIR) {
			for (Supplier<? extends Item> egg : NESTS.keySet()) {
				if (item == egg.get()) {
					return NESTS.get(egg);
				}
			}
		}

		return null;
	}
}