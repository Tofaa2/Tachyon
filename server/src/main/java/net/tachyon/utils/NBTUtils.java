package net.tachyon.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.tachyon.Tachyon;
import net.tachyon.attribute.Attribute;
import net.tachyon.attribute.AttributeOperation;
import net.tachyon.chat.ChatParser;
import net.tachyon.data.DataType;
import net.tachyon.inventory.Inventory;
import net.tachyon.item.Enchantment;
import net.tachyon.item.ItemStack;
import net.tachyon.item.Material;
import net.tachyon.item.attribute.ItemAttribute;
import net.tachyon.item.metadata.ItemMeta;
import net.tachyon.registry.Registries;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import net.tachyon.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

// for lack of a better name
public final class NBTUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(NBTUtils.class);

    private NBTUtils() {

    }

    /**
     * Loads all the items from the 'items' list into the given inventory
     *
     * @param items       the items to save
     * @param destination the inventory destination
     */
    public static void loadAllItems(@NotNull NBTList<NBTCompound> items, @NotNull Inventory destination) {
        destination.clear();
        for (NBTCompound tag : items) {
            Material item = Registries.getMaterial(tag.getString("id"));
            if (item == Material.AIR) {
                item = Material.STONE;
            }
            ItemStack itemStack = ItemStack.of(item, tag.getByte("Count"));
            if (tag.containsKey("tag")) {
                loadDataIntoItem(itemStack, tag.getCompound("tag"));
            }
            destination.setItemStack(tag.getByte("Slot"), itemStack);
        }
    }

    public static void saveAllItems(@NotNull NBTList<NBTCompound> list, @NotNull Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            final ItemStack stack = inventory.getItemStack(i);
            NBTCompound nbt = new NBTCompound();

            NBTCompound tag = new NBTCompound();
            saveDataIntoNBT(stack, tag);

            nbt.set("tag", tag);
            nbt.setByte("Slot", (byte) i);
            nbt.setByte("Count", stack.amount());
            nbt.setString("id", stack.material().getName());

            list.add(nbt);
        }
    }

    public static void writeEnchant(@NotNull NBTCompound nbt, @NotNull String listName,
                                    @NotNull Map<Enchantment, Short> enchantmentMap) {
        NBTList<NBTCompound> enchantList = new NBTList<>(NBTTypes.TAG_Compound);
        for (Map.Entry<Enchantment, Short> entry : enchantmentMap.entrySet()) {
            final Enchantment enchantment = entry.getKey();
            final short level = entry.getValue();

            enchantList.add(new NBTCompound()
                    .setShort("lvl", level)
                    .setShort("id", (short) enchantment.getId())
            );
        }
        nbt.set(listName, enchantList);
    }

    @NotNull
    public static ItemStack readItemStack(@NotNull TachyonBinaryReader reader) {
        final short id = reader.readShort();

        if (id == -1) {
            return ItemStack.AIR;
        }

        final Material material = Material.fromId(id);
        final byte count = reader.readByte();
        final short damage = reader.readShort();
        ItemStack item = ItemStack.of(material, count, damage);

        try {
            final NBT itemNBT = reader.readTag();
            if (itemNBT instanceof NBTCompound nbt) { // can also be a TAG_End if no data
                loadDataIntoItem(item, nbt);
            }
        } catch (IOException | NBTException e) {
            Tachyon.getServer().getExceptionManager().handleException(e);
        }

        return item;
    }

    @SuppressWarnings("ConstantConditions")
    public static ItemStack loadDataIntoItem(@NotNull ItemStack item, @NotNull NBTCompound nbt) {
        ItemStack.Builder builder = item.toBuilder();
        if (nbt.containsKey("Unbreakable")) builder.unbreakable(nbt.getAsByte("Unbreakable") == 1);
        if (nbt.containsKey("HideFlags")) builder.rawFlags(nbt.getInt("HideFlags"));
        if (nbt.containsKey("display")) {
            final NBTCompound display = nbt.getCompound("display");
            if (display.containsKey("Name")) {
                final String rawName = display.getString("Name");
                final Component displayName = ChatParser.toComponent(rawName);
                builder.displayName(displayName);
            }
            if (display.containsKey("Lore")) {
                NBTList<NBTString> loreList = display.getList("Lore");
                List<Component> lore = new ArrayList<>();
                for (NBTString s : loreList) {
                    lore.add(ChatParser.toComponent(s.getValue()));
                }
                builder.lore(lore);
            }
        }

        // Enchantments
        if (nbt.containsKey("ench")) {
            Map<Enchantment, Short> enchants = new HashMap<>();

            loadEnchantments(nbt.getList("ench"), enchants::put);
            builder.enchantments(enchants);
        }

        // Attributes
        if (nbt.containsKey("AttributeModifiers")) {
            NBTList<NBTCompound> attributes = nbt.getList("AttributeModifiers");
            final List<ItemAttribute> attributesList = new ArrayList<>();
            for (NBTCompound attributeNBT : attributes) {
                final UUID uuid;
                {
                    final int[] uuidArray = attributeNBT.getIntArray("UUID");
                    uuid = Utils.intArrayToUuid(uuidArray);
                }

                final double value = attributeNBT.getAsDouble("Amount");
                final String slot = attributeNBT.containsKey("Slot") ? attributeNBT.getString("Slot") : "MAINHAND";
                final String attributeName = attributeNBT.getString("AttributeName");
                final int operation = attributeNBT.getAsInt("Operation");
                final String name = attributeNBT.getString("Name");

                final Attribute attribute = Attribute.fromKey(attributeName);
                // Wrong attribute name, stop here
                if (attribute == null)
                    break;
                final AttributeOperation attributeOperation = AttributeOperation.fromId(operation);
                // Wrong attribute operation, stop here
                if (attributeOperation == null) {
                    break;
                }

                // Add attribute
                final ItemAttribute itemAttribute = new ItemAttribute(uuid, name, attribute, attributeOperation, value);
                attributesList.add(itemAttribute);
            }
            builder.itemAttributes(attributesList);
        }

        // Hide flags
        {
            if (nbt.containsKey("HideFlags")) {
                builder.rawFlags(nbt.getInt("HideFlags"));
            }
        }

        // Meta specific field
        final ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.read(nbt);
        }

        // TODO: implement ownership
//        // Ownership
//        {
//            if (nbt.containsKey(TachyonItemStack.OWNERSHIP_DATA_KEY)) {
//                final String identifierString = nbt.getString(TachyonItemStack.OWNERSHIP_DATA_KEY);
//                final UUID identifier = UUID.fromString(identifierString);
//                final Data data = TachyonItemStack.DATA_OWNERSHIP.getOwnObject(identifier);
//                if (data != null) {
//                    item.setData(data);
//                }
//            }
//        }

        //CanPlaceOn
        {
            if (nbt.containsKey("CanPlaceOn")) {
                NBTList<NBTString> canPlaceOn = nbt.getList("CanPlaceOn");
                canPlaceOn.forEach(x -> {
                    builder.addPlaceOn(x.getValue());
                });
            }
        }
        //CanDestroy
        {
            if (nbt.containsKey("CanDestroy")) {
                NBTList<NBTString> canPlaceOn = nbt.getList("CanDestroy");
                canPlaceOn.forEach(x -> {
                    builder.addDestroyOn(x.getValue());
                });            }
        }
        return builder.build();
    }

    public static void loadEnchantments(NBTList<NBTCompound> enchantments, EnchantmentSetter setter) {
        for (NBTCompound enchantment : enchantments) {
            final short level = enchantment.getAsShort("lvl");
            final short id = enchantment.getAsShort("id");
            final Enchantment enchant = Enchantment.fromId(id);
            if (enchant != null) {
                setter.applyEnchantment(enchant, level);
            } else {
                LOGGER.warn("Unknown enchantment type: {}", id);
            }
        }
    }

    public static void writeItemStack(TachyonBinaryWriter packet, ItemStack itemStack) {
        if (itemStack == null || itemStack.material() == Material.AIR) {
            packet.writeShort((short) -1);
        } else {
            packet.writeShort(itemStack.material().getId());
            packet.writeByte(itemStack.amount());
            packet.writeShort(itemStack.damage());

            if (!itemStack.hasNbtTag()) {
                packet.writeByte((byte) NBTTypes.TAG_End); // No nbt
                return;
            }

            NBTCompound itemNBT = new NBTCompound();

            // Vanilla compound
            saveDataIntoNBT(itemStack, itemNBT);

            // End custom model data
            packet.writeNBT("", itemNBT);
        }
    }

    public static void saveDataIntoNBT(@NotNull ItemStack itemStack, @NotNull NBTCompound itemNBT) {
        // Unbreakable
        if (itemStack.isUnbreakable()) {
            itemNBT.setInt("Unbreakable", 1);
        }

        // Display
        final boolean hasDisplayName = itemStack.hasDisplayName();
        final boolean hasLore = itemStack.hasLore();

        if (hasDisplayName || hasLore) {
            NBTCompound displayNBT = new NBTCompound();
            if (hasDisplayName) {
                final String name = LegacyComponentSerializer.legacySection().serialize(itemStack.displayName());
                displayNBT.setString("Name", name);
            }

            if (hasLore) {
                final Collection<Component> lore = itemStack.lore();

                final NBTList<NBTString> loreNBT = new NBTList<>(NBTTypes.TAG_String);
                for (Component line : lore) {
                    loreNBT.add(new NBTString(LegacyComponentSerializer.legacySection().serialize(line)));
                }
                displayNBT.set("Lore", loreNBT);
            }

            itemNBT.set("display", displayNBT);
        }
        // End display

        // Start enchantment
        {
            final Map<Enchantment, Short> enchantmentMap = itemStack.getEnchantmentsMap();
            if (!enchantmentMap.isEmpty()) {
                writeEnchant(itemNBT, "ench", enchantmentMap);
            }
        }
        // End enchantment

        // Start attribute
        {
            final Collection<ItemAttribute> itemAttributes = itemStack.getItemAttributes();
            if (!itemAttributes.isEmpty()) {
                NBTList<NBTCompound> attributesNBT = new NBTList<>(NBTTypes.TAG_Compound);

                for (ItemAttribute itemAttribute : itemAttributes) {
                    final UUID uuid = itemAttribute.getUuid();
                    attributesNBT.add(
                            new NBTCompound()
                                    .setLong("UUIDMost", uuid.getMostSignificantBits())
                                    .setLong("UUIDLeast", uuid.getLeastSignificantBits())
                                    .setDouble("Amount", itemAttribute.getValue())
                                    .setString("AttributeName", itemAttribute.getAttribute().getKey())
                                    .setInt("Operation", itemAttribute.getOperation().getId())
                                    .setString("Name", itemAttribute.getInternalName())
                    );
                }
                itemNBT.set("AttributeModifiers", attributesNBT);
            }
        }
        // End attribute

        // Start hide flags
        {
            final int hideFlag = itemStack.getRawFlags();
            if (hideFlag != 0) {
                itemNBT.setInt("HideFlags", hideFlag);
            }
        }
        // End hide flags

        // Start custom meta
        {
            final ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.write(itemNBT);
            }
        }
        // End custom meta

//        // Start ownership
//        {
//            final Data data = itemStack.getData();
//            if (data != null && !data.isEmpty()) {
//                final UUID identifier = itemStack.getIdentifier();
//                itemNBT.setString(TachyonItemStack.OWNERSHIP_DATA_KEY, identifier.toString());
//            }
//        }
//        // End ownership

        //CanDestroy
        {
            Set<String> canDestroy = itemStack.getCanDestroy();
            if (canDestroy.size() > 0) {
                NBTList<NBTString> list = new NBTList<>(NBTTypes.TAG_String);
                canDestroy.forEach(x -> list.add(new NBTString(x)));
                itemNBT.set("CanDestroy", list);
            }
        }

        //CanDestroy
        {
            Set<String> canPlaceOn = itemStack.getCanPlaceOn();
            if (canPlaceOn.size() > 0) {
                NBTList<NBTString> list = new NBTList<>(NBTTypes.TAG_String);
                canPlaceOn.forEach(x -> list.add(new NBTString(x)));
                itemNBT.set("CanPlaceOn", list);
            }
        }
    }

    /**
     * Converts an object into its {@link NBT} equivalent.
     * <p>
     * If {@code type} is not a primitive type or primitive array and {@code supportDataType} is true,
     * the data will be encoded with the appropriate {@link DataType} into a byte array.
     *
     * @param value           the value to convert
     * @param type            the type of the value, used to know which {@link DataType} to use if {@code value} is not a primitive type
     * @param supportDataType true to allow using a {@link DataType} to encode {@code value} into a byte array if not a primitive type
     * @return the converted value, null if {@code type} is not a primitive type and {@code supportDataType} is false
     */
    @Nullable
    public static NBT toNBT(@NotNull Object value, @NotNull Class type, boolean supportDataType) {
        type = PrimitiveConversion.getObjectClass(type);
        if (type.equals(Boolean.class)) {
            // No boolean type in NBT
            return new NBTByte((byte) (((boolean) value) ? 1 : 0));
        } else if (type.equals(Byte.class)) {
            return new NBTByte((byte) value);
        } else if (type.equals(Character.class)) {
            // No char type in NBT
            return new NBTShort((short) value);
        } else if (type.equals(Short.class)) {
            return new NBTShort((short) value);
        } else if (type.equals(Integer.class)) {
            return new NBTInt((int) value);
        } else if (type.equals(Long.class)) {
            return new NBTLong((long) value);
        } else if (type.equals(Float.class)) {
            return new NBTFloat((float) value);
        } else if (type.equals(Double.class)) {
            return new NBTDouble((double) value);
        } else if (type.equals(String.class)) {
            return new NBTString((String) value);
        } else if (type.equals(Byte[].class)) {
            return new NBTByteArray((byte[]) value);
        } else if (type.equals(Integer[].class)) {
            return new NBTIntArray((int[]) value);
        } else if (type.equals(Long[].class)) {
            return new NBTLongArray((long[]) value);
        } else {
            if (supportDataType) {
                // Custom NBT type, try to encode using the data manager
                DataType dataType = Tachyon.getServer().getDataManager().getDataType(type);
                Check.notNull(dataType, "The type '" + type + "' is not registered in TachyonDataManager and not a primitive type.");

                TachyonBinaryWriter writer = new TachyonBinaryWriter();
                dataType.encode(writer, value);

                final byte[] encodedValue = writer.toByteArray();

                return new NBTByteArray(encodedValue);
            } else {
                return null;
            }
        }
    }

    /**
     * Converts a nbt object to its raw value.
     * <p>
     * Currently support number, string, byte/int/long array.
     *
     * @param nbt the nbt tag to convert
     * @return the value representation of a tag
     * @throws UnsupportedOperationException if the tag type is not supported
     */
    @NotNull
    public static Object fromNBT(@NotNull NBT nbt) {
        if (nbt instanceof NBTNumber) {
            return ((NBTNumber) nbt).getValue();
        } else if (nbt instanceof NBTString) {
            return ((NBTString) nbt).getValue();
        } else if (nbt instanceof NBTByteArray) {
            return ((NBTByteArray) nbt).getValue();
        } else if (nbt instanceof NBTIntArray) {
            return ((NBTIntArray) nbt).getValue();
        } else if (nbt instanceof NBTLongArray) {
            return ((NBTLongArray) nbt).getValue();
        }

        throw new UnsupportedOperationException("NBT type " + nbt.getClass() + " is not handled properly.");
    }

    @FunctionalInterface
    public interface EnchantmentSetter {
        void applyEnchantment(Enchantment name, short level);
    }
}
