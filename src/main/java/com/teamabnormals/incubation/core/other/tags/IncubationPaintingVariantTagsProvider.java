package com.teamabnormals.incubation.core.other.tags;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.datapack.IncubationPaintingVariants;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class IncubationPaintingVariantTagsProvider extends PaintingVariantTagsProvider {

	public IncubationPaintingVariantTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Incubation.MOD_ID, helper);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(PaintingVariantTags.PLACEABLE).add(IncubationPaintingVariants.EGG, IncubationPaintingVariants.CULPRIT);
	}
}
