package com.teamabnormals.incubation.core.registry;

import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import com.teamabnormals.incubation.common.item.ScrambledEggsItem;
import com.teamabnormals.incubation.core.Incubation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Incubation.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IncubationItems {
	public static final ItemSubRegistryHelper HELPER = Incubation.REGISTRY_HELPER.getItemSubHelper();

	public static final RegistryObject<Item> FRIED_EGG = HELPER.createItem("fried_egg", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(EnvironmentalFoods.FRIED_EGG)));
	public static final RegistryObject<Item> SCRAMBLED_EGGS = HELPER.createItem("scrambled_eggs", () -> new ScrambledEggsItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_FOOD).food(EnvironmentalFoods.SCRAMBLED_EGGS)));

	public static final class EnvironmentalFoods {
		public static final FoodProperties FRIED_EGG = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).build();
		public static final FoodProperties SCRAMBLED_EGGS = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).build();
	}
}