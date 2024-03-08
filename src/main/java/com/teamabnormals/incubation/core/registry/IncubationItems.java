package com.teamabnormals.incubation.core.registry;

import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import com.teamabnormals.incubation.common.item.ScrambledEggsItem;
import com.teamabnormals.incubation.core.Incubation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.item.CreativeModeTabs.FOOD_AND_DRINKS;
import static net.minecraft.world.item.CreativeModeTabs.NATURAL_BLOCKS;
import static net.minecraft.world.item.crafting.Ingredient.of;

@EventBusSubscriber(modid = Incubation.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IncubationItems {
	public static final ItemSubRegistryHelper HELPER = Incubation.REGISTRY_HELPER.getItemSubHelper();

	public static final RegistryObject<Item> FRIED_EGG = HELPER.createItem("fried_egg", () -> new Item(new Item.Properties().food(EnvironmentalFoods.FRIED_EGG)));
	public static final RegistryObject<Item> SCRAMBLED_EGGS = HELPER.createItem("scrambled_eggs", () -> new ScrambledEggsItem(new Item.Properties().stacksTo(1).food(EnvironmentalFoods.SCRAMBLED_EGGS)));

	public static void setupTabEditors() {
		CreativeModeTabContentsPopulator.mod(Incubation.MOD_ID)
				.tab(FOOD_AND_DRINKS)
				.addItemsBefore(of(Items.BREAD), FRIED_EGG)
				.addItemsAfter(of(Items.RABBIT_STEW), SCRAMBLED_EGGS)
				.tab(NATURAL_BLOCKS)
				.addItemsAfter(of(Blocks.FLOWERING_AZALEA), IncubationBlocks.TWIG_NEST, IncubationBlocks.HAY_NEST)
				.addItemsAfter(of(Blocks.HAY_BLOCK), IncubationBlocks.CHICKEN_EGG_CRATE, IncubationBlocks.TURTLE_EGG_CRATE);
	}

	public static final class EnvironmentalFoods {
		public static final FoodProperties FRIED_EGG = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).build();
		public static final FoodProperties SCRAMBLED_EGGS = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).build();
	}
}