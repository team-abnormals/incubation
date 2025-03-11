package com.teamabnormals.incubation.core.data.client;

import com.teamabnormals.blueprint.core.data.client.BlueprintLanguageProvider;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationItems;
import com.teamabnormals.incubation.core.registry.datapack.IncubationPaintingVariants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;

import static com.teamabnormals.incubation.core.registry.IncubationBlocks.*;

public class IncubationLanguageProvider extends BlueprintLanguageProvider {

	public IncubationLanguageProvider(PackOutput output) {
		super(output, Incubation.MOD_ID);
	}

	@Override
	public void addTranslations() {
		this.add(
				CHICKEN_EGG_CRATE.get(), TURTLE_EGG_CRATE.get(),
				HAY_NEST.get(), HAY_CHICKEN_NEST.get(), HAY_DUCK_NEST.get(), HAY_TURKEY_NEST.get(),
				TWIG_NEST.get(), TWIG_CHICKEN_NEST.get(), TWIG_DUCK_NEST.get(), TWIG_TURKEY_NEST.get()
		);
		this.add(IncubationItems.FRIED_EGG.get(), IncubationItems.SCRAMBLED_EGGS.get());
		this.add("item.incubation.chicken_egg", "Chicken Egg");

		this.add(IncubationPaintingVariants.EGG, "Egg", "five");
		this.add(IncubationPaintingVariants.CULPRIT, "Culprit", "five");
	}

	private void add(ResourceKey<PaintingVariant> variant, String title, String author) {
		ResourceLocation name = variant.location();
		String key = "painting." + name.getNamespace() + "." + name.getPath() + ".";
		this.add(key + "title", title);
		this.add(key + "author", author);
	}
}