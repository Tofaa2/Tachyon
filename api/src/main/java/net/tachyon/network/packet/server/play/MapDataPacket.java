package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class MapDataPacket implements ServerPacket {

    public int mapId;
    public byte scale;

    public Icon[] icons;

    public short columns;
    public short rows;
    public byte x;
    public byte z;
    public byte[] data;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(mapId);
        writer.writeByte(scale);

        if (icons != null && icons.length > 0) {
            writer.writeVarInt(icons.length);
            for (Icon icon : icons) {
                icon.write(writer);
            }
        } else {
            writer.writeVarInt(0);
        }

        writer.writeByte((byte) columns);
        if (columns <= 0) {
            return;
        }

        writer.writeByte((byte) rows);
        writer.writeByte(x);
        writer.writeByte(z);
        if (data != null && data.length > 0) {
            writer.writeVarInt(data.length);
            writer.writeBytes(data);
        } else {
            writer.writeVarInt(0);
        }

    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.MAP_DATA;
    }

    public static class Icon {
        public int type;
        public byte direction;
        public byte x, z;

        private void write(BinaryWriter writer) {
            writer.writeByte((byte) ((type & 0x0F) | (direction & 0xF0)));
            writer.writeByte(x);
            writer.writeByte(z);
        }

    }

}
