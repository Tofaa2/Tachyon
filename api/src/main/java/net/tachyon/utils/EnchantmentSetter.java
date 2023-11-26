package net.tachyon.utils;

import net.tachyon.item.Enchantment;

@FunctionalInterface
public interface EnchantmentSetter {
    void applyEnchantment(Enchantment name, short level);


}
