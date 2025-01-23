package com.teamabnormals.incubation.core.other.tags;

import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationPaintingVariants;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.CompletableFuture;

public class IncubationPaintingVariantTagsProvider extends PaintingVariantTagsProvider {

	public IncubationPaintingVariantTagsProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider, ExistingFileHelper helper) {
		super(packOutput, lookupProvider, Incubation.MOD_ID, helper);
	}

	@Override
	protected void addTags(Provider provider) {
		TagAppender<PaintingVariant> appender = this.tag(PaintingVariantTags.PLACEABLE);
		for (RegistryObject<PaintingVariant> variant : IncubationPaintingVariants.PAINTING_VARIANTS.getEntries()) {
			appender.add(variant.getKey());
		}
	}
}
