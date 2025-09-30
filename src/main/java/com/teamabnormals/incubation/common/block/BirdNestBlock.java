package com.teamabnormals.incubation.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BirdNestBlock extends Block implements WorldlyContainerHolder {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
	public static final IntegerProperty EGGS = IntegerProperty.create("eggs", 1, 6);
	private final Supplier<? extends Item> egg;
	private final EmptyNestBlock emptyNest;

	public BirdNestBlock(Supplier<? extends Item> eggIn, EmptyNestBlock emptyNestIn, Properties properties) {
		super(properties);
		this.egg = eggIn;
		this.emptyNest = emptyNestIn;
		this.emptyNest.addNest(this.egg, this);

		this.registerDefaultState(this.stateDefinition.any().setValue(EGGS, 1));
	}

	public BirdNestBlock(ResourceLocation eggIn, EmptyNestBlock emptyNestIn, Properties properties) {
		this(() -> BuiltInRegistries.ITEM.get(eggIn), emptyNestIn, properties);
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
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (player.mayBuild()) {
			if (this.egg.get() != Items.AIR && stack.is(this.egg.get())) {
				int i = state.getValue(EGGS);
				if (i < 6) {
					if (!player.getAbilities().instabuild) {
						stack.shrink(1);
					}
					level.setBlock(pos, state.setValue(EGGS, i + 1), 3);
					return ItemInteractionResult.sidedSuccess(level.isClientSide);
				} else {
					return ItemInteractionResult.CONSUME;
				}
			} else {
				popResource(level, pos, new ItemStack(this.egg.get()));
				removeEgg(player, state, level, pos);
				return ItemInteractionResult.sidedSuccess(level.isClientSide);
			}
		} else {
			return super.useItemOn(stack, state, level, pos, player, handIn, hit);
		}
	}

	public static BlockState removeEgg(@Nullable Entity entity, BlockState state, LevelAccessor level, BlockPos pos) {
		int i = state.getValue(EGGS);
		BlockState newState;
		if (i > 1) {
			newState = state.setValue(EGGS, i - 1);
		} else {
			newState = ((BirdNestBlock) state.getBlock()).getEmptyNest().defaultBlockState();
		}
		level.setBlock(pos, newState, 3);
		level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, newState));
		return newState;
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
		return new ItemStack(this.getEgg());
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).isSolid();
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide() && !player.isCreative() && this.getEgg() != null && state.getValue(EGGS) > 0) {
			popResource(level, pos, new ItemStack(this.getEgg(), state.getValue(EGGS)));
		}
		return super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return null;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(EGGS);
	}

	public Item getEgg() {
		return this.egg.get();
	}

	public EmptyNestBlock getEmptyNest() {
		return this.emptyNest;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
		return blockState.getValue(EGGS);
	}

	@Override
	public WorldlyContainer getContainer(BlockState state, LevelAccessor level, BlockPos pos) {
		return new BirdNestBlock.OutputContainer(state, level, pos, new ItemStack(this.getEgg()));
	}

	public static class OutputContainer extends SimpleContainer implements WorldlyContainer {
		private final BlockState state;
		private final LevelAccessor level;
		private final BlockPos pos;
		private final ItemStack eggStack;
		private boolean changed;

		public OutputContainer(BlockState state, LevelAccessor level, BlockPos pos, ItemStack stack) {
			super(stack);
			this.state = state;
			this.level = level;
			this.pos = pos;
			this.eggStack = stack;
		}

		@Override
		public int getMaxStackSize() {
			return 1;
		}

		@Override
		public int[] getSlotsForFace(Direction side) {
			return side == Direction.DOWN ? new int[]{0} : new int[0];
		}

		@Override
		public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
			return false;
		}

		@Override
		public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
			return !this.changed && direction == Direction.DOWN && stack.is(this.eggStack.getItem());
		}

		@Override
		public void setChanged() {
			BirdNestBlock.removeEgg(null, this.state, this.level, this.pos);
			this.changed = true;
		}
	}
}