package net.tachyon.command.arguments.minecraft.registry;

import net.tachyon.Tachyon;
import net.tachyon.potion.PotionEffect;
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
        return Tachyon.getUnsafe().getPotionEffect(value);
    }

}
