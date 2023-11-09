package net.tachyon.data.type;

import net.kyori.adventure.text.Component;
import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.chat.Adventure;
import net.tachyon.data.DataType;
import net.tachyon.inventory.Inventory;
import net.tachyon.inventory.InventoryType;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

public class InventoryData extends DataType<Inventory> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull Inventory value) {
        final InventoryType inventoryType = value.getInventoryType();
        final int size = inventoryType.getAdditionalSlot();

        // Inventory title & type
        writer.writeSizedString(Adventure.COMPONENT_SERIALIZER.serialize(value.getTitle()));
        writer.writeSizedString(inventoryType.name());

        // Write all item stacks
        for (int i = 0; i < size; i++) {
            writer.writeItemStack(value.getItemStack(i));
        }
    }

    @NotNull
    @Override
    public Inventory decode(@NotNull BinaryReader reader) {
        final Component title = Adventure.COMPONENT_SERIALIZER.deserialize(reader.readSizedString(Integer.MAX_VALUE));
        final InventoryType inventoryType = InventoryType.valueOf(reader.readSizedString(Integer.MAX_VALUE));
        final int size = inventoryType.getAdditionalSlot();

        Inventory inventory = new Inventory(inventoryType, title);

        // Read all item stacks
        for (int i = 0; i < size; i++) {
            inventory.setItemStack(i, reader.readSlot());
        }

        return inventory;
    }
}
