package com.teamabnormals.incubation.core.registry;

import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.incubation.common.block.BirdNestBlock;
import com.teamabnormals.incubation.common.block.EmptyNestBlock;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.IncubationConstants;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public class IncubationBlocks {
	public static final BlockSubRegistryHelper BLOCKS = Incubation.REGISTRY_HELPER.getBlockSubHelper();

	public static final DeferredBlock<Block> TWIG_NEST = BLOCKS.createBlock("twig_nest", () -> new EmptyNestBlock(IncubationProperties.TWIG_NEST));
	public static final DeferredBlock<Block> TWIG_CHICKEN_NEST = BLOCKS.createBlockNoItem("twig_chicken_nest", () -> new BirdNestBlock(() -> Items.EGG, (EmptyNestBlock) TWIG_NEST.get(), IncubationProperties.TWIG_NEST));
	public static final DeferredBlock<Block> TWIG_DUCK_NEST = BLOCKS.createBlockNoItem("twig_duck_nest", () -> new BirdNestBlock(IncubationConstants.DUCK_EGG, (EmptyNestBlock) TWIG_NEST.get(), IncubationProperties.TWIG_NEST));
	public static final DeferredBlock<Block> TWIG_TURKEY_NEST = BLOCKS.createBlockNoItem("twig_turkey_nest", () -> new BirdNestBlock(IncubationConstants.TURKEY_EGG, (EmptyNestBlock) TWIG_NEST.get(), IncubationProperties.TWIG_NEST));

	public static final DeferredBlock<Block> HAY_NEST = BLOCKS.createBlock("hay_nest", () -> new EmptyNestBlock(IncubationProperties.HAY_NEST));
	public static final DeferredBlock<Block> HAY_CHICKEN_NEST = BLOCKS.createBlockNoItem("hay_chicken_nest", () -> new BirdNestBlock(() -> Items.EGG, (EmptyNestBlock) HAY_NEST.get(), IncubationProperties.HAY_NEST));
	public static final DeferredBlock<Block> HAY_DUCK_NEST = BLOCKS.createBlockNoItem("hay_duck_nest", () -> new BirdNestBlock(IncubationConstants.DUCK_EGG, (EmptyNestBlock) HAY_NEST.get(), IncubationProperties.HAY_NEST));
	public static final DeferredBlock<Block> HAY_TURKEY_NEST = BLOCKS.createBlockNoItem("hay_turkey_nest", () -> new BirdNestBlock(IncubationConstants.TURKEY_EGG, (EmptyNestBlock) HAY_NEST.get(), IncubationProperties.HAY_NEST));

	public static final DeferredBlock<Block> CHICKEN_EGG_CRATE = BLOCKS.createBlock("chicken_egg_crate", () -> new Block(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));
	public static final DeferredBlock<Block> TURTLE_EGG_CRATE = BLOCKS.createBlock("turtle_egg_crate", () -> new Block(Block.Properties.of().mapColor(MapColor.SAND).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));

	public static final class IncubationProperties {
		public static final Block.Properties TWIG_NEST = Block.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.GRASS);
		public static final Block.Properties HAY_NEST = Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS);
	}
}
