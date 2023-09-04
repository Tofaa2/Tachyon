package net.tachyon.command.builder.arguments.minecraft.registry;

import net.tachyon.potion.PotionEffect;
import net.tachyon.registry.Registries;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an argument giving a {@link PotionEffect}.
 */
public class ArgumentPotionEffect extends ArgumentRegistry<PotionEffect> {

    public ArgumentPotionEffect(String id) {
        super(id);
    }

    @Override
    public PotionEffect getRegistry(@NotNull String value) {
        return Registries.getPotionEffect(value);
    }

}
