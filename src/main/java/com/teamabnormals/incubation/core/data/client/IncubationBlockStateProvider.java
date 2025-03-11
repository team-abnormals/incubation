package com.teamabnormals.incubation.core.data.client;

import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import com.teamabnormals.incubation.common.block.BirdNestBlock;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class IncubationBlockStateProvider extends BlueprintBlockStateProvider {

	public IncubationBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Incubation.MOD_ID, helper);
	}

	@Override
	public void registerStatesAndModels() {
		this.cubeBottomTopBlock(IncubationBlocks.CHICKEN_EGG_CRATE);
		this.cubeBottomTopBlock(IncubationBlocks.TURTLE_EGG_CRATE);

		this.nestBlocks(IncubationBlocks.TWIG_NEST, IncubationBlocks.HAY_NEST);
		this.nestBlocks("chicken", IncubationBlocks.TWIG_CHICKEN_NEST, IncubationBlocks.HAY_CHICKEN_NEST);
		this.nestBlocks("duck", IncubationBlocks.TWIG_DUCK_NEST, IncubationBlocks.HAY_DUCK_NEST);
		this.nestBlocks("turkey", IncubationBlocks.TWIG_TURKEY_NEST, IncubationBlocks.HAY_TURKEY_NEST);
	}

	public void nestBlocks(String eggType, DeferredBlock<Block> twigNest, DeferredBlock<Block> hayNest) {
		this.eggNest(eggType, IncubationBlocks.TWIG_NEST, twigNest);
		this.eggNest(eggType, IncubationBlocks.HAY_NEST, hayNest);
	}

	public void nestBlocks(DeferredBlock<Block> twigNest, DeferredBlock<Block> hayNest) {
		this.eggNest(null, IncubationBlocks.TWIG_NEST, twigNest);
		this.eggNest(null, IncubationBlocks.HAY_NEST, hayNest);
	}

	public void eggNest(String eggType, DeferredBlock<Block> base, DeferredBlock<Block> nest) {
		if (eggType != null) {
			MultiPartBlockStateBuilder builder = this.getMultipartBuilder(nest.get()).part().modelFile(new UncheckedModelFile(blockTexture(base.get()))).addModel().end();
			String[] names = new String[]{"one", "two", "three", "four", "five", "six"};
			for (int i = 0; i < 6; i++) {
				builder.part().modelFile(new UncheckedModelFile(Incubation.location("block/" + names[i] + "_" + eggType + "_egg" + (i > 0 ? "s" : "")))).addModel().condition(BirdNestBlock.EGGS, i + 1).end();
			}
		} else {
			this.simpleBlock(base.get(), new UncheckedModelFile(blockTexture(base.get())));
			this.generatedItem(nest.get(), "item");
		}
	}
}