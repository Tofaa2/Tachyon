package net.tachyon.command.builder.arguments.minecraft.registry;

import net.tachyon.item.Enchantment;
import net.tachyon.registry.Registries;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an argument giving an {@link Enchantment}.
 */
public class ArgumentEnchantment extends ArgumentRegistry<Enchantment> {

    public ArgumentEnchantment(String id) {
        super(id);
    }

    @Override
    public Enchantment getRegistry(@NotNull String value) {
        return Registries.getEnchantment(value);
    }

}
