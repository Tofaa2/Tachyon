package net.tachyon.utils;

import io.netty.buffer.ByteBuf;
import net.tachyon.utils.binary.TachyonBinaryWriter;

import java.util.UUID;

public final class Utils {

    private Utils() {

    }

    public static int getVarIntSize(int input) {
        return (input & 0xFFFFFF80) == 0
                ? 1 : (input & 0xFFFFC000) == 0
                ? 2 : (input & 0xFFE00000) == 0
                ? 3 : (input & 0xF0000000) == 0
                ? 4 : 5;
    }

    public static void writeVarIntBuf(ByteBuf buffer, int value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            buffer.writeByte(temp);
        } while (value != 0);
    }

    public static void writeVarInt(TachyonBinaryWriter writer, int value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            writer.writeByte(temp);
        } while (value != 0);
    }

    public static int readVarInt(ByteBuf buffer) {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = buffer.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static void writeVarLong(TachyonBinaryWriter writer, long value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            writer.writeByte(temp);
        } while (value != 0);
    }

    public static int[] uuidToIntArray(UUID uuid) {
        int[] array = new int[4];

        final long uuidMost = uuid.getMostSignificantBits();
        final long uuidLeast = uuid.getLeastSignificantBits();

        array[0] = (int) (uuidMost >> 32);
        array[1] = (int) uuidMost;

        array[2] = (int) (uuidLeast >> 32);
        array[3] = (int) uuidLeast;

        return array;
    }

    public static UUID intArrayToUuid(int[] array) {
        final long uuidMost = (long) array[0] << 32 | array[1] & 0xFFFFFFFFL;
        final long uuidLeast = (long) array[2] << 32 | array[3] & 0xFFFFFFFFL;

        return new UUID(uuidMost, uuidLeast);
    }

}
