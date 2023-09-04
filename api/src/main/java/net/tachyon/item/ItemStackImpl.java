package net.tachyon.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.tachyon.item.attribute.ItemAttribute;
import net.tachyon.item.metadata.*;
import net.tachyon.item.rule.StackingRule;
import net.tachyon.item.rule.VanillaStackingRule;
import net.tachyon.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTList;
import org.jglrxavpok.hephaistos.nbt.NBTString;
import org.jglrxavpok.hephaistos.nbt.NBTTypes;

import java.util.*;
import java.util.function.Consumer;

record ItemStackImpl(Material material, byte amount, Collection<Component> lore, Component displayName, short damage,
                     Map<Enchantment, Short> enchantments, int hideflag, Collection<ItemAttribute> attributes,
                     Set<String> canPlaceOn, Set<String> canDestroy, ItemMeta meta,
                     boolean unbreakable, StackingRule rule) implements ItemStack {


    private static final StackingRule VANILLA_STACKING_RULE = new VanillaStackingRule(64);
    static final ItemStack AIR = new Builder().material(Material.AIR).build();

    @Override
    public @NotNull ItemStack withMaterial(@NotNull Material material) {
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public @Nullable ItemMeta getItemMeta() {
        return meta;
    }

    @Override
    public @NotNull <T extends ItemMeta> ItemStack withItemMeta(Class<T> metaClass, @NotNull Consumer<T> metaConsumer) {
        Check.notNull(meta, "This ItemStack has no meta to be modified");
        ItemMeta meta = this.meta.clone();
        if (metaClass.isInstance(meta)) {
            metaConsumer.accept((T) meta);
        }
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public @NotNull ItemStack withAmount(byte amount) {
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public boolean isUnbreakable() {
        return unbreakable;
    }

    @Override
    public @NotNull ItemStack withDamage(short damage) {
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public @NotNull Collection<Component> lore() {
        return lore;
    }

    @Override
    public @NotNull ItemStack withLore(@NotNull Collection<Component> lore) {
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public boolean hasLore() {
        return !lore.isEmpty();
    }

    @Override
    public @NotNull Component displayName() {
        return displayName;
    }

    @Override
    public @NotNull ItemStack withDisplayName(@Nullable Component displayName) {
        Component name = displayName == null ? Component.text(material.getName()) : displayName;
        return new ItemStackImpl(
                material, amount, lore, name, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public @NotNull ItemStack replaceMeta(@NotNull ItemMeta meta) {
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public boolean canDestroy(@NotNull String block) {
        return canDestroy.isEmpty() || canDestroy.contains(block);
    }

    @Override
    public boolean canPlaceOn(@NotNull String block) {
        return canPlaceOn.isEmpty() || canPlaceOn.contains(block);
    }

    @Override
    public boolean hasDisplayName() {
        return !displayName.equals(Component.text(material.getName()));
    }

    @Override
    public @NotNull Map<Enchantment, Short> getEnchantmentsMap() {
        return Map.copyOf(enchantments);
    }

    @Override
    public @NotNull ItemStack withEnchantment(@NotNull Enchantment enchantment, short level) {
        Map<Enchantment, Short> enchantments = new HashMap<>(this.enchantments);
        enchantments.put(enchantment, level);
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public @NotNull ItemStack withoutEnchantment(@NotNull Enchantment enchantment) {
        Map<Enchantment, Short> enchantments = new HashMap<>(this.enchantments);
        enchantments.remove(enchantment);
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public short getEnchantmentLevel(@NotNull Enchantment enchantment) {
        return enchantments.getOrDefault(enchantment, (short) 0);
    }

    @Override
    public boolean hasNbtTag() {
        return hasDisplayName() || hasLore()
                || isUnbreakable() || !enchantments.isEmpty()
                || !attributes.isEmpty() || hideflag != 0 ||
        ((meta != null && meta.hasNbt())) || !canPlaceOn.isEmpty() || !canDestroy.isEmpty();
    }

    @Override
    public @NotNull NBTCompound toNBT() {
        NBTCompound compound = new NBTCompound()
                .setByte("Cound", amount)
                .setString("id", material.getName());
        if (damage != 0) compound.setShort("Damage", damage);
        if (hasNbtTag()) {
            NBTCompound meta = new NBTCompound();
            if (isUnbreakable()) {
                meta.setInt("Unbreakable", 1);
            }
            final boolean hasDisplay = hasDisplayName();
            final boolean hasLore = hasLore();
            if (hasDisplay || hasLore) {
                NBTCompound display = new NBTCompound();
                if (hasDisplay) {
                    final String name = LegacyComponentSerializer.legacySection().serialize(displayName);
                    display.setString("Name", name);
                }
                if (hasLore) {
                    final NBTList<NBTString> lore = new NBTList<>(NBTTypes.TAG_String);
                    for (Component line : this.lore) {
                        lore.add(new NBTString(LegacyComponentSerializer.legacySection().serialize(line)));
                    }
                    display.set("Lore", lore);
                }
                meta.set("display", display);
            }
            if (!enchantments.isEmpty()) {

            }
            compound.set("tag", meta);
        }
        return compound;
    }

    @Override
    public @NotNull Collection<ItemFlag> getItemFlags() {
        Set<ItemFlag> currentFlags = EnumSet.noneOf(ItemFlag.class);
        for (ItemFlag flag : ItemFlag.values()) {
            if (hasItemFlag(flag)) {
                currentFlags.add(flag);
            }
        }
        return currentFlags;
    }

    @Override
    public boolean hasItemFlag(@NotNull ItemFlag flag) {
        final int bitModifier = getBitModifier(flag);
        return (this.hideflag & bitModifier) == bitModifier;
    }

    @Override
    public @NotNull Collection<ItemAttribute> getItemAttributes() {
        return Collections.unmodifiableCollection(attributes);
    }

    @Override
    public @NotNull ItemStack withAttribute(@NotNull ItemAttribute attribute) {
        Collection<ItemAttribute> attributes = new ArrayList<>(this.attributes);
        attributes.add(attribute);
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public @NotNull ItemStack withoutAttribute(@NotNull ItemAttribute attribute) {
        Collection<ItemAttribute> attributes = new ArrayList<>(this.attributes);
        attributes.remove(attribute);
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public @NotNull ItemStack withItemFlag(@NotNull ItemFlag flag) {
        int newFlags = hideflag | getBitModifier(flag);
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                newFlags, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public @NotNull ItemStack withoutItemFlag(@NotNull ItemFlag flag) {
        int newFlags = hideflag & ~getBitModifier(flag);
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                newFlags, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public @NotNull StackingRule getStackingRule() {
        return rule;
    }

    @Override
    public @NotNull ItemStack withStackingRule(@NotNull StackingRule stackingRule) {
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, stackingRule
        );
    }

    @Override
    public int getRawFlags() {
        return hideflag;
    }

    @Override
    public @NotNull Set<String> getCanDestroy() {
        return Set.copyOf(canDestroy);
    }

    @Override
    public @NotNull Set<String> getCanPlaceOn() {
        return Set.copyOf(canPlaceOn);
    }


    @Override
    public boolean isSimilar(@NotNull ItemStack other) {
        return false;
    }

    @Override
    public @NotNull ItemStack withUnbreakableModifier(boolean unbreakable) {
        return new ItemStackImpl(
                material, amount, lore, displayName, damage, enchantments,
                hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, rule
        );
    }

    @Override
    public ItemStack.@NotNull Builder toBuilder() {
        ItemStackImpl.Builder builder = new Builder();
        builder.material = material;
        builder.amount = amount;
        builder.damage = damage;
        builder.lore = lore;
        builder.displayName = displayName;
        builder.enchantments.putAll(enchantments);
        builder.hideflag = hideflag;
        builder.attributes = attributes;
        builder.canPlaceOn.addAll(canPlaceOn);
        builder.canDestroy.addAll(canDestroy);
        builder.meta = meta;
        builder.unbreakable = unbreakable;
        return builder;
    }

    private static byte getBitModifier(@NotNull ItemFlag hideFlag) {
        return (byte) (1 << hideFlag.ordinal());
    }

    static final class Builder implements ItemStack.Builder {

        private Material material = Material.AIR;
        private int amount = 1;
        private short damage = 0;
        private Collection<Component> lore = Collections.emptyList();
        private Component displayName = Component.text(material.getName());
        private final Map<Enchantment, Short> enchantments = new HashMap<>(0);
        private ItemMeta meta = findMeta();
        private int hideflag = 0;
        private Collection<ItemAttribute> attributes = Collections.emptyList();
        private final Set<String> canPlaceOn = new HashSet<>(0);
        private final Set<String> canDestroy = new HashSet<>(0);
        private boolean unbreakable = false;
        private StackingRule stackingRule = VANILLA_STACKING_RULE;

        @Override
        public ItemStack.@NotNull Builder material(@NotNull Material material) {
            this.material = material;
            return this;
        }

        @Override
        public ItemStack.@NotNull Builder amount(byte amount) {
            this.amount = amount;
            return this;
        }


        @Override
        public ItemStack.@NotNull Builder damage(short damage) {
            this.damage = damage;
            return this;
        }

        @Override
        public ItemStack.@NotNull Builder lore(@NotNull Collection<Component> lore) {
            this.lore = lore;
            return this;
        }

        @Override
        public ItemStack.@NotNull Builder displayName(@Nullable Component displayName) {
            this.displayName = displayName == null ? Component.text(material.getName()) : displayName;
            return this;
        }

        @Override
        public ItemStack.@NotNull Builder enchantment(@NotNull Enchantment enchantment, short level) {
            this.enchantments.put(enchantment, level);
            return this;
        }

        @Override
        public ItemStack.@NotNull Builder enchantments(@NotNull Map<Enchantment, Short> enchantments) {
            this.enchantments.putAll(enchantments);
            return this;
        }

        @Override
        public <T extends ItemMeta> ItemStack.@NotNull Builder itemMeta(Class<T> metaClass, @NotNull Consumer<T> metaConsumer) {
            if (metaClass.isInstance(meta)) {
                metaConsumer.accept((T) meta);
            }
            return this;
        }

        @Override
        public ItemStack.@NotNull Builder rawFlags(int flags) {
            this.hideflag = flags;
            return this;
        }

        @Override
        public ItemStack.@NotNull Builder itemFlags(@NotNull Collection<ItemFlag> flags) {
            for (ItemFlag flag : flags) {
                this.hideflag |= getBitModifier(flag);
            }
            return this;
        }


        @Override
        public ItemStack.@NotNull Builder itemAttributes(@NotNull Collection<ItemAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        @Override
        public ItemStack.@NotNull Builder unbreakable(boolean unbreakable) {
            this.unbreakable = unbreakable;
            return this;
        }

        @Override
        public ItemStack.@NotNull Builder stackingRule(@NotNull StackingRule stackingRule) {
            this.stackingRule = stackingRule;
            return this;
        }

        @Override
        public ItemStack.@NotNull Builder addDestroyOn(@NotNull String block) {
            this.canDestroy.add(block);
            return this;
        }

        @Override
        public ItemStack.@NotNull Builder addPlaceOn(@NotNull String block) {
            this.canPlaceOn.add(block);
            return this;
        }

        @Override
        public @NotNull ItemStack build() {
            return new ItemStackImpl(
                    material, (byte) amount, lore, displayName, damage, enchantments,
                    hideflag, attributes, canPlaceOn, canDestroy, meta, unbreakable, stackingRule
            );
        }

        @Nullable
        private ItemMeta findMeta() {
            return switch (material) {
                case FILLED_MAP -> new MapMeta();
                case WRITTEN_BOOK -> new WrittenBookMeta();
                case WRITABLE_BOOK -> new WritableBookMeta();
                //case ENCHANTED_BOOK -> new EnchantedBookMeta();
                case FIREWORK_CHARGE -> new FireworkEffectMeta();
                case FIREWORKS -> new FireworkMeta();
                //case SKULL -> new SkullMeta();
                case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS -> new LeatherArmorMeta();
                //case POTION -> new PotionMeta();
                case COMPASS -> new CompassMeta();
                default -> null;
            };
        }
    }

}
