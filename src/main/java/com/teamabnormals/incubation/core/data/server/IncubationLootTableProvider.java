package com.teamabnormals.incubation.core.data.server;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.Tags;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IncubationLootTableProvider extends LootTableProvider {

	public IncubationLootTableProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, BuiltInLootTables.all(), ImmutableList.of(
				new LootTableProvider.SubProviderEntry(IncubationBlockLoot::new, LootContextParamSets.BLOCK)
		), provider);
	}

	@Override
	protected void validate(WritableRegistry<LootTable> registry, ValidationContext context, ProblemReporter.Collector collector) {
	}

	private static class IncubationBlockLoot extends BlockLootSubProvider {
		private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEAR));
		private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.AIR).map(ItemLike::asItem).collect(Collectors.toSet());

		protected IncubationBlockLoot(Provider provider) {
			super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), provider);
		}

		@Override
		public void generate() {
			this.dropSelf(IncubationBlocks.CHICKEN_EGG_CRATE.get());
			this.dropSelf(IncubationBlocks.TURTLE_EGG_CRATE.get());
			this.dropSelf(IncubationBlocks.HAY_NEST.get());
			this.dropOther(IncubationBlocks.HAY_CHICKEN_NEST.get(), IncubationBlocks.HAY_NEST.get());
			this.dropOther(IncubationBlocks.HAY_DUCK_NEST.get(), IncubationBlocks.HAY_NEST.get());
			this.dropOther(IncubationBlocks.HAY_TURKEY_NEST.get(), IncubationBlocks.HAY_NEST.get());
			this.add(IncubationBlocks.TWIG_NEST.get(), this.createTwigNestDrops(IncubationBlocks.TWIG_NEST.get()));
			this.add(IncubationBlocks.TWIG_CHICKEN_NEST.get(), this.createTwigNestDrops(IncubationBlocks.TWIG_CHICKEN_NEST.get()));
			this.add(IncubationBlocks.TWIG_DUCK_NEST.get(), this.createTwigNestDrops(IncubationBlocks.TWIG_DUCK_NEST.get()));
			this.add(IncubationBlocks.TWIG_TURKEY_NEST.get(), this.createTwigNestDrops(IncubationBlocks.TWIG_TURKEY_NEST.get()));
		}

		protected LootTable.Builder createShearsDispatchTable(Block block, LootPoolEntryContainer.Builder<?> builder) {
			return createSelfDropDispatchTable(block, HAS_SHEARS, builder);
		}

		protected LootTable.Builder createTwigNestDrops(Block block) {
			return createShearsDispatchTable(IncubationBlocks.TWIG_NEST.get(), applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))));
		}

		@Override
		public Iterable<Block> getKnownBlocks() {
			return BuiltInRegistries.BLOCK.stream().filter(block -> Incubation.MOD_ID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace())).collect(Collectors.toSet());
		}
	}
}