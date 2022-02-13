package com.teamabnormals.incubation.core.registry;

import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.incubation.common.block.BirdNestBlock;
import com.teamabnormals.incubation.common.block.EmptyNestBlock;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.other.IncubationConstants;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Incubation.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IncubationBlocks {
	public static final BlockSubRegistryHelper HELPER = Incubation.REGISTRY_HELPER.getBlockSubHelper();

	public static final RegistryObject<Block> TWIG_NEST = HELPER.createBlock("twig_nest", () -> new EmptyNestBlock(IncubationProperties.TWIG_NEST), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> TWIG_CHICKEN_NEST = HELPER.createBlockNoItem("twig_chicken_nest", () -> new BirdNestBlock(() -> Items.EGG, (EmptyNestBlock) TWIG_NEST.get(), IncubationProperties.TWIG_NEST));
	public static final RegistryObject<Block> TWIG_DUCK_NEST = HELPER.createBlockNoItem("twig_duck_nest", () -> new BirdNestBlock(IncubationConstants.DUCK_EGG, (EmptyNestBlock) TWIG_NEST.get(), IncubationProperties.TWIG_NEST));
	public static final RegistryObject<Block> TWIG_TURKEY_NEST = HELPER.createBlockNoItem("twig_turkey_nest", () -> new BirdNestBlock(IncubationConstants.TURKEY_EGG, (EmptyNestBlock) TWIG_NEST.get(), IncubationProperties.TWIG_NEST));

	public static final RegistryObject<Block> HAY_NEST = HELPER.createBlock("hay_nest", () -> new EmptyNestBlock(IncubationProperties.HAY_NEST), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> HAY_CHICKEN_NEST = HELPER.createBlockNoItem("hay_chicken_nest", () -> new BirdNestBlock(() -> Items.EGG, (EmptyNestBlock) HAY_NEST.get(), IncubationProperties.HAY_NEST));
	public static final RegistryObject<Block> HAY_DUCK_NEST = HELPER.createBlockNoItem("hay_duck_nest", () -> new BirdNestBlock(IncubationConstants.DUCK_EGG, (EmptyNestBlock) HAY_NEST.get(), IncubationProperties.HAY_NEST));
	public static final RegistryObject<Block> HAY_TURKEY_NEST = HELPER.createBlockNoItem("hay_turkey_nest", () -> new BirdNestBlock(IncubationConstants.TURKEY_EGG, (EmptyNestBlock) HAY_NEST.get(), IncubationProperties.HAY_NEST));

	public static final RegistryObject<Block> CHICKEN_EGG_CRATE = HELPER.createBlock("chicken_egg_crate", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).strength(1.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> TURTLE_EGG_CRATE = HELPER.createBlock("turtle_egg_crate", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.SAND).strength(1.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_DECORATIONS);

	public static final class IncubationProperties {
		public static final Block.Properties TWIG_NEST = Block.Properties.of(Material.GRASS, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.GRASS);
		public static final Block.Properties HAY_NEST = Block.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS);
	}
}
