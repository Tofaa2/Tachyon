package net.tachyon.command.arguments.minecraft.registry;

import net.tachyon.Tachyon;
import net.tachyon.item.Enchantment;
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
        return Tachyon.getUnsafe().getEnchantment(value);
    }

}
