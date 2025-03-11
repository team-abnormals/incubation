package com.teamabnormals.incubation.core.data.server;

import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class IncubationRecipeProvider extends RecipeProvider {

	public IncubationRecipeProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider);
	}

	@Override
	public void buildRecipes(RecipeOutput output) {
		nineBlockStorageRecipes(output, RecipeCategory.BUILDING_BLOCKS, Items.EGG, IncubationBlocks.CHICKEN_EGG_CRATE.get());
		nineBlockStorageRecipes(output, RecipeCategory.BUILDING_BLOCKS, Items.TURTLE_EGG, IncubationBlocks.TURTLE_EGG_CRATE.get());
		cookingRecipes(output, RecipeCategory.FOOD, Tags.Items.EGGS, IncubationItems.FRIED_EGG.get());
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IncubationItems.SCRAMBLED_EGGS.get()).requires(Tags.Items.EGGS).requires(Tags.Items.EGGS).requires(BlueprintItemTags.MILK).requires(Items.BOWL).unlockedBy(getHasName(Items.BOWL), has(Items.BOWL)).unlockedBy(getHasName(Tags.Items.EGGS), has(Tags.Items.EGGS)).save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IncubationBlocks.HAY_NEST.get()).define('#', Items.WHEAT).pattern("# #").pattern("###").unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT)).save(output);
	}

	protected static void nineBlockStorageRecipes(RecipeOutput output, RecipeCategory category, ItemLike item, ItemLike block) {
		nineBlockStorageRecipes(output, category, item, block, getItemName(block), null, getItemName(item), null);
	}

	protected static void nineBlockStorageRecipes(RecipeOutput output, RecipeCategory category, ItemLike item, ItemLike block, String shapedName, @Nullable String shapedGroup, String shapelessName, @Nullable String shapelessGroup) {
		ShapelessRecipeBuilder.shapeless(category, item, 9).requires(block).group(shapelessGroup).unlockedBy(getHasName(block), has(block)).save(output, Incubation.location(shapelessName));
		ShapedRecipeBuilder.shaped(category, block).define('#', item).pattern("###").pattern("###").pattern("###").group(shapedGroup).unlockedBy(getHasName(item), has(item)).save(output, Incubation.location(shapedName));
	}

	private static void cookingRecipes(RecipeOutput output, RecipeCategory category, TagKey<Item> tag, ItemLike result) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(tag), category, result, 0.35F, 200).unlockedBy(getHasName(tag), has(tag)).save(output);
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(tag), category, result, 0.35F, 100).unlockedBy(getHasName(tag), has(tag)).save(output, Incubation.location(getItemName(result.asItem()) + "_from_smoking"));
		SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(tag), category, result, 0.35F, 600).unlockedBy(getHasName(tag), has(tag)).save(output, Incubation.location(getItemName(result.asItem()) + "_from_campfire_cooking"));
	}

	protected static Criterion<TriggerInstance> has(TagKey<Item> tag) {
		return inventoryTrigger(ItemPredicate.Builder.item().of(tag).build());
	}

	protected static String getHasName(ItemLike item) {
		return "has_" + getItemName(item);
	}

	private static String getHasName(TagKey<Item> item) {
		return "has_" + item.location().getPath();
	}
}