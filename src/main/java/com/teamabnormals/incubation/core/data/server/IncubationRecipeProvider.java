package com.teamabnormals.incubation.core.data.server;

import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class IncubationRecipeProvider extends RecipeProvider {

	public IncubationRecipeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		nineBlockStorageRecipes(consumer, Items.EGG, IncubationBlocks.CHICKEN_EGG_CRATE.get());
		nineBlockStorageRecipes(consumer, Items.TURTLE_EGG, IncubationBlocks.TURTLE_EGG_CRATE.get());
		cookingRecipes(consumer, BlueprintItemTags.EGGS, IncubationItems.FRIED_EGG.get());
		ShapelessRecipeBuilder.shapeless(IncubationItems.SCRAMBLED_EGGS.get()).requires(BlueprintItemTags.EGGS).requires(BlueprintItemTags.EGGS).requires(BlueprintItemTags.MILK).requires(Items.BOWL).unlockedBy(getHasName(Items.BOWL), has(Items.BOWL)).unlockedBy(getHasName(BlueprintItemTags.EGGS), has(BlueprintItemTags.EGGS)).save(consumer);
		ShapedRecipeBuilder.shaped(IncubationBlocks.HAY_NEST.get()).define('#', Items.WHEAT).pattern("# #").pattern("###").unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT)).save(consumer);
	}

	private static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ItemLike item, ItemLike block) {
		nineBlockStorageRecipes(consumer, item, block, getItemName(block), null, getItemName(item), null);
	}

	private static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ItemLike item, ItemLike block, String shapedName, @Nullable String shapedGroup, String shapelessName, @Nullable String shapelessGroup) {
		ShapelessRecipeBuilder.shapeless(item, 9).requires(block).group(shapelessGroup).unlockedBy(getHasName(block), has(block)).save(consumer, new ResourceLocation(Incubation.MOD_ID, shapelessName));
		ShapedRecipeBuilder.shaped(block).define('#', item).pattern("###").pattern("###").pattern("###").group(shapedGroup).unlockedBy(getHasName(item), has(item)).save(consumer, new ResourceLocation(Incubation.MOD_ID, shapedName));
	}

	private static void cookingRecipes(Consumer<FinishedRecipe> consumer, Tag.Named<Item> tag, ItemLike result) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(tag), result, 0.35F, 200).unlockedBy(getHasName(tag), has(tag)).save(consumer);
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(tag), result, 0.35F, 100).unlockedBy(getHasName(tag), has(tag)).save(consumer, new ResourceLocation(Incubation.MOD_ID, getItemName(result.asItem()) + "_from_smoking"));
		SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(tag), result, 0.35F, 600).unlockedBy(getHasName(tag), has(tag)).save(consumer, new ResourceLocation(Incubation.MOD_ID, getItemName(result.asItem()) + "_from_campfire_cooking"));
	}

	private static String getHasName(ItemLike item) {
		return "has_" + getItemName(item);
	}

	private static String getHasName(Tag.Named<Item> item) {
		return "has_" + item.getName().getPath();
	}

	private static String getItemName(ItemLike item) {
		return Registry.ITEM.getKey(item.asItem()).getPath();
	}
}