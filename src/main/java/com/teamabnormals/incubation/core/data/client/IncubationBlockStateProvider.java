package com.teamabnormals.incubation.core.data.client;

import com.teamabnormals.incubation.common.block.BirdNestBlock;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class IncubationBlockStateProvider extends BlockStateProvider {

	public IncubationBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Incubation.MOD_ID, existingFileHelper);
	}

	@Override
	public void registerStatesAndModels() {
		this.cubeBottomTop(IncubationBlocks.CHICKEN_EGG_CRATE.get());
		this.cubeBottomTop(IncubationBlocks.TURTLE_EGG_CRATE.get());

		this.nestBlocks(IncubationBlocks.TWIG_NEST.get(), IncubationBlocks.HAY_NEST.get());
		this.nestBlocks("chicken", IncubationBlocks.TWIG_CHICKEN_NEST.get(), IncubationBlocks.HAY_CHICKEN_NEST.get());
		this.nestBlocks("duck", IncubationBlocks.TWIG_DUCK_NEST.get(), IncubationBlocks.HAY_DUCK_NEST.get());
		this.nestBlocks("turkey", IncubationBlocks.TWIG_TURKEY_NEST.get(), IncubationBlocks.HAY_TURKEY_NEST.get());
	}

	public void cubeBottomTop(Block block) {
		ResourceLocation name = ForgeRegistries.BLOCKS.getKey(block);
		this.cubeBottomTop(block, prefix("block/", suffix(name, "_side")), prefix("block/", suffix(name, "_bottom")), prefix("block/", suffix(name, "_top")));
	}

	public void cubeBottomTop(Block block, ResourceLocation sideTexture, ResourceLocation bottomTexture, ResourceLocation topTexture) {
		this.simpleBlock(block, this.models().cubeBottomTop(name(block), sideTexture, bottomTexture, topTexture));
		this.blockItem(block);
	}

	public void blockItem(Block block) {
		this.simpleBlockItem(block, new ExistingModelFile(blockTexture(block), this.models().existingFileHelper));
	}

	public void nestBlocks(String eggType, Block twigNest, Block hayNest) {
		this.eggNest(eggType, IncubationBlocks.TWIG_NEST.get(), twigNest);
		this.eggNest(eggType, IncubationBlocks.HAY_NEST.get(), hayNest);
	}

	public void nestBlocks(Block twigNest, Block hayNest) {
		this.eggNest(null, IncubationBlocks.TWIG_NEST.get(), twigNest);
		this.eggNest(null, IncubationBlocks.HAY_NEST.get(), hayNest);
	}

	public ModelFile blockModelFile(String name) {
		return new UncheckedModelFile(new ResourceLocation(Incubation.MOD_ID, "block/" + name));
	}

	public void simpleBlockWithModel(Block block) {
		this.simpleBlock(block, blockModelFile(name(block)));
	}

	public void eggNest(String eggType, Block base, Block nest) {
		if (eggType != null) {
			this.getMultipartBuilder(nest)
					.part().modelFile(blockModelFile(name(base))).addModel().end()
					.part().modelFile(blockModelFile("one_" + eggType + "_egg")).addModel().condition(BirdNestBlock.EGGS, 1).end()
					.part().modelFile(blockModelFile("two_" + eggType + "_eggs")).addModel().condition(BirdNestBlock.EGGS, 2).end()
					.part().modelFile(blockModelFile("three_" + eggType + "_eggs")).addModel().condition(BirdNestBlock.EGGS, 3).end()
					.part().modelFile(blockModelFile("four_" + eggType + "_eggs")).addModel().condition(BirdNestBlock.EGGS, 4).end()
					.part().modelFile(blockModelFile("five_" + eggType + "_eggs")).addModel().condition(BirdNestBlock.EGGS, 5).end()
					.part().modelFile(blockModelFile("six_" + eggType + "_eggs")).addModel().condition(BirdNestBlock.EGGS, 6).end();
		} else {
			this.simpleBlockWithModel(base);
			this.generated(nest);
		}
	}

	private void generated(ItemLike item) {
		ResourceLocation itemName = ForgeRegistries.ITEMS.getKey(item.asItem());
		itemModels().withExistingParent(itemName.getPath(), "item/generated").texture("layer0", new ResourceLocation(Incubation.MOD_ID, "item/" + itemName.getPath()));
	}

	private String name(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block).getPath();
	}

	private ResourceLocation prefix(String prefix, ResourceLocation rl) {
		return new ResourceLocation(rl.getNamespace(), prefix + rl.getPath());
	}

	private ResourceLocation suffix(ResourceLocation rl, String suffix) {
		return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
	}
}