package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.entity.ArmorSlot;
import net.tachyon.item.ItemStack;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record EntityEquipmentPacket(int entityId, Slot slot, ItemStack itemStack) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);

        short slotEnum = (short) slot.ordinal();

        writer.writeShort(slotEnum);
        writer.writeItemStack(itemStack);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_EQUIPMENT;
    }

    public enum Slot {
        HAND,
        BOOTS,
        LEGGINGS,
        CHESTPLATE,
        HELMET;

        @NotNull
        public static Slot fromArmorSlot(ArmorSlot armorSlot) {
            return switch (armorSlot) {
                case HELMET -> HELMET;
                case CHESTPLATE -> CHESTPLATE;
                case LEGGINGS -> LEGGINGS;
                case BOOTS -> BOOTS;
            };
        }

    }

}
