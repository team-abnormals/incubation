package com.teamabnormals.incubation.core.data.server;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class IncubationLootTableProvider extends LootTableProvider {
	private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> tables = ImmutableList.of(Pair.of(BlockProvider::new, LootContextParamSets.BLOCK));

	public IncubationLootTableProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	public List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
		return tables;
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {
	}

	private static class BlockProvider extends BlockLoot {
		private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS));

		@Override
		public void addTables() {
			this.dropSelf(IncubationBlocks.CHICKEN_EGG_CRATE.get());
			this.dropSelf(IncubationBlocks.TURTLE_EGG_CRATE.get());
			this.dropSelf(IncubationBlocks.HAY_NEST.get());
			this.dropOther(IncubationBlocks.HAY_CHICKEN_NEST.get(), IncubationBlocks.HAY_NEST.get());
			this.dropOther(IncubationBlocks.HAY_DUCK_NEST.get(), IncubationBlocks.HAY_NEST.get());
			this.dropOther(IncubationBlocks.HAY_TURKEY_NEST.get(), IncubationBlocks.HAY_NEST.get());
			this.add(IncubationBlocks.TWIG_NEST.get(), BlockProvider::createTwigNestDrops);
			this.add(IncubationBlocks.TWIG_CHICKEN_NEST.get(), BlockProvider::createTwigNestDrops);
			this.add(IncubationBlocks.TWIG_DUCK_NEST.get(), BlockProvider::createTwigNestDrops);
			this.add(IncubationBlocks.TWIG_TURKEY_NEST.get(), BlockProvider::createTwigNestDrops);
		}

		protected static LootTable.Builder createShearsDispatchTable(Block block, LootPoolEntryContainer.Builder<?> builder) {
			return createSelfDropDispatchTable(block, HAS_SHEARS, builder);
		}

		protected static LootTable.Builder createTwigNestDrops(Block block) {
			return createShearsDispatchTable(IncubationBlocks.TWIG_NEST.get(), applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))));
		}

		@Override
		public Iterable<Block> getKnownBlocks() {
			return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block.getRegistryName() != null && Incubation.MOD_ID.equals(block.getRegistryName().getNamespace())).collect(Collectors.toSet());
		}
	}
}