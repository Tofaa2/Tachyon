package net.tachyon.item.metadata;

import it.unimi.dsi.fastutil.objects.Object2ShortMap;
import it.unimi.dsi.fastutil.objects.Object2ShortOpenHashMap;
import kotlin.Suppress;
import net.tachyon.Tachyon;
import net.tachyon.item.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTList;
import org.jglrxavpok.hephaistos.nbt.NBTTypes;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.Collections;
import java.util.Map;

public class EnchantedBookMeta extends ItemMeta {

    private final Object2ShortMap<Enchantment> storedEnchantmentMap = new Object2ShortOpenHashMap<>();

    /**
     * Gets the stored enchantment map.
     * Stored enchantments are used on enchanted book.
     *
     * @return an unmodifiable map containing the item stored enchantments
     */
    @NotNull
    public Map<Enchantment, Short> getStoredEnchantmentMap() {
        return Collections.unmodifiableMap(storedEnchantmentMap);
    }

    /**
     * Sets a stored enchantment level.
     *
     * @param enchantment the enchantment type
     * @param level       the enchantment level
     */
    public void setStoredEnchantment(@NotNull Enchantment enchantment, short level) {
        if (level < 1) {
            removeStoredEnchantment(enchantment);
            return;
        }

        this.storedEnchantmentMap.put(enchantment, level);
    }

    /**
     * Removes a stored enchantment.
     *
     * @param enchantment the enchantment type
     */
    public void removeStoredEnchantment(@NotNull Enchantment enchantment) {
        this.storedEnchantmentMap.removeShort(enchantment);
    }

    /**
     * Gets a stored enchantment level.
     *
     * @param enchantment the enchantment type
     * @return the stored enchantment level, 0 if not present
     */
    public int getStoredEnchantmentLevel(@NotNull Enchantment enchantment) {
        return this.storedEnchantmentMap.getOrDefault(enchantment, (short) 0);
    }

    @Override
    public boolean hasNbt() {
        return !storedEnchantmentMap.isEmpty();
    }

    @Override
    public boolean isSimilar(@NotNull ItemMeta itemMeta) {
        return itemMeta instanceof EnchantedBookMeta &&
                ((EnchantedBookMeta) itemMeta).storedEnchantmentMap.equals(storedEnchantmentMap);
    }

    @Override
    public void read(@NotNull NBTCompound compound) {
        if (compound.containsKey("StoredEnchantments")) {
            NBTList<NBTCompound> enchantList = compound.getList("StoredEnchantments");
            for (NBTCompound enchantment : enchantList) {
                final short level = enchantment.getAsShort("lvl");
                final short id = enchantment.getAsShort("id");
                final Enchantment enchant = Enchantment.fromId(id);
                if (enchant != null) {
                    this.setStoredEnchantment(enchant, level);
                } else {
                    Tachyon.LOGGER.warn("Unknown enchantment type: {}", id);
                }
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void write(@NotNull NBTCompound compound) {
        if (!storedEnchantmentMap.isEmpty()) {
            NBTList<NBTCompound> enchantList = new NBTList<>(NBTTypes.TAG_Compound);
            for (var entry : storedEnchantmentMap.entrySet()) {
                final Enchantment enchantment = entry.getKey();
                final short level = entry.getValue();

                enchantList.add(new NBTCompound()
                        .setShort("lvl", level)
                        .setShort("id", (short) enchantment.getId())
                );
            }
            compound.set("StoredEnchantments", enchantList);
        }
    }

    @NotNull
    @Override
    public ItemMeta clone() {
        EnchantedBookMeta enchantedBookMeta = (EnchantedBookMeta) super.clone();
        enchantedBookMeta.storedEnchantmentMap.putAll(storedEnchantmentMap);

        return enchantedBookMeta;
    }
}
