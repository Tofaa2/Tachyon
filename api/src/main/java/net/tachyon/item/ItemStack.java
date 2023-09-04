package net.tachyon.item;

import net.kyori.adventure.text.Component;
import net.tachyon.item.attribute.ItemAttribute;
import net.tachyon.item.metadata.ItemMeta;
import net.tachyon.item.rule.StackingRule;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public sealed interface ItemStack permits ItemStackImpl {

    @NotNull ItemStack AIR = ItemStackImpl.AIR;

    static @NotNull Builder builder() {
        return new ItemStackImpl.Builder();
    }

    static @NotNull Builder builder(@NotNull Material material) {
        return new ItemStackImpl.Builder().material(material);
    }

    static @NotNull ItemStack of(@NotNull Material material) {
        return ItemStack.builder(material).build();
    }

    static @NotNull ItemStack of(@NotNull Material material, int amount) {
        return ItemStack.builder(material).amount((byte) amount).build();
    }

    static @NotNull ItemStack of(@NotNull Material material, int amount, short damage) {
        return ItemStack.builder(material).amount((byte) amount).damage(damage).build();
    }

    @NonNull Material material();

    @NotNull ItemStack withMaterial(@NotNull Material material);


    @Nullable ItemMeta getItemMeta();

    @NotNull <T extends ItemMeta> ItemStack withItemMeta(Class<T> metaClass, @NotNull Consumer<T> metaConsumer);

    byte amount();

    @NotNull ItemStack withAmount(byte amount);

    boolean hasNbtTag();

    short damage();

    @NotNull ItemStack withDamage(short damage);

    @NotNull Collection<Component> lore();

    @NotNull ItemStack withLore(@NotNull Collection<Component> lore);

    boolean hasLore();

    @NotNull Component displayName();

    @NotNull ItemStack withDisplayName(@Nullable Component displayName);

    boolean hasDisplayName();
    
    @NotNull Map<Enchantment, Short> getEnchantmentsMap();

    @NotNull ItemStack withEnchantment(@NotNull Enchantment enchantment, short level);

    @NotNull ItemStack withoutEnchantment(@NotNull Enchantment enchantment);

    short getEnchantmentLevel(@NotNull Enchantment enchantment);

    @NotNull NBTCompound toNBT();

    @NotNull Collection<ItemFlag> getItemFlags();

    boolean hasItemFlag(@NotNull ItemFlag flag);

    @NotNull Collection<ItemAttribute> getItemAttributes();

    @NotNull ItemStack withAttribute(@NotNull ItemAttribute attribute);

    @NotNull ItemStack withoutAttribute(@NotNull ItemAttribute attribute);

    @NotNull ItemStack withItemFlag(@NotNull ItemFlag flag);

    @NotNull ItemStack withoutItemFlag(@NotNull ItemFlag flag);

    @NotNull StackingRule getStackingRule();

    @NotNull ItemStack withStackingRule(@NotNull StackingRule stackingRule);

    @NotNull ItemStack replaceMeta(@NotNull ItemMeta meta);

    int getRawFlags();

    @NotNull Set<String> getCanDestroy();

    boolean canDestroy(@NotNull String block);

    @NotNull Set<String> getCanPlaceOn();

    boolean canPlaceOn(@NotNull String block);

    boolean isSimilar(@NotNull ItemStack other);

    boolean isUnbreakable();

    @NotNull ItemStack withUnbreakableModifier(boolean unbreakable);

    default boolean isAir() {
        return material() == Material.AIR;
    }

    @NotNull Builder toBuilder();

    interface Builder {

        @NotNull Builder material(@NotNull Material material);

        @NotNull Builder amount(byte amount);

        @NotNull Builder damage(short damage);

        @NotNull Builder lore(@NotNull Collection<Component> lore);

        @NotNull Builder displayName(@Nullable Component displayName);

        @NotNull Builder enchantment(@NotNull Enchantment enchantment, short level);

        @NotNull Builder enchantments(@NotNull Map<Enchantment, Short> enchantments);

        @NotNull <T extends ItemMeta> Builder itemMeta(Class<T> metaClass, @NotNull Consumer<T> metaConsumer);

        default @NotNull Builder itemMeta(@NotNull Consumer<ItemMeta> metaConsumer) {
            return itemMeta(ItemMeta.class, metaConsumer);
        }

        @NotNull Builder itemFlags(@NotNull Collection<ItemFlag> flags);

        @NotNull Builder itemAttributes(@NotNull Collection<ItemAttribute> attributes);

        @NotNull Builder unbreakable(boolean unbreakable);

        @NotNull Builder addPlaceOn(@NotNull String block);

        @NotNull Builder addDestroyOn(@NotNull String block);

        @NotNull Builder rawFlags(int flags);

        @NotNull Builder stackingRule(@NotNull StackingRule rule);

        @NotNull ItemStack build();

    }
}
