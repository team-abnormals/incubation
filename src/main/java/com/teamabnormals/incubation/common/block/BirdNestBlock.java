package com.teamabnormals.incubation.common.block;

import com.teamabnormals.incubation.common.block.entity.BirdNestBlockEntity;
import com.teamabnormals.incubation.core.registry.IncubationBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BirdNestBlock extends BaseEntityBlock {
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
		this(() -> ForgeRegistries.ITEMS.getValue(eggIn), emptyNestIn, properties);
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
			int i = state.getValue(EGGS);
			ItemStack itemstack = player.getItemInHand(handIn);
			if (this.egg.get() != Items.AIR && itemstack.getItem() == this.egg.get()) {
				if (i < 6) {
					if (!player.getAbilities().instabuild) {
						itemstack.shrink(1);
					}
					worldIn.setBlock(pos, state.setValue(EGGS, i + 1), 3);
				}
			} else {
				popResource(worldIn, pos, new ItemStack(this.egg.get()));

				if (i > 1)
					worldIn.setBlock(pos, state.setValue(EGGS, i - 1), 3);
				else
					worldIn.setBlock(pos, this.getEmptyNest().defaultBlockState(), 3);
			}
			return InteractionResult.sidedSuccess(worldIn.isClientSide);
		} else {
			return super.use(state, worldIn, pos, player, handIn, hit);
		}
	}

	public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(this.getEgg());
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
	}

	@Override
	public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
		super.playerWillDestroy(worldIn, pos, state, player);
		if (!worldIn.isClientSide && !player.isCreative() && this.getEgg() != null && state.getValue(EGGS) > 0)
			popResource(worldIn, pos, new ItemStack(this.getEgg(), state.getValue(EGGS)));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BirdNestBlockEntity(pos, state);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return level.isClientSide ? null : createTickerHelper(type, IncubationBlockEntityTypes.BIRD_NEST.get(), BirdNestBlockEntity::serverTick);
	}

	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

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
}