package net.tachyon.binary;

import net.tachyon.coordinate.Point;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBT;

import java.util.UUID;
import java.util.function.Consumer;

public interface BinaryWriter {

    void writeBoolean(boolean b);

    void writeByte(byte b);

    void writeChar(char c);

    void writeShort(short s);

    void writeInt(int i);

    void writeLong(long l);

    void writeFloat(float f);

    void writeDouble(double d);

    void writeVarInt(int i);

    void writeVarLong(long l);

    void writeSizedString(@NotNull String s);

    void writeVarIntArray(int[] array);

    void writeBytes(byte[] bytes);

    void writeItemStack(@NotNull ItemStack itemStack);

    void writeStringArray(@NotNull String[] array);

    void writeUuid(UUID uuid);

    void writePoint(Point point);

    void writePoint(int x, int y, int z);

    void writeNBT(String name, NBT tag);

    byte[] toByteArray();

    void writeAtStart(BinaryWriter writer);

    void writeAtEnd(BinaryWriter writer);

    void write(Consumer<BinaryWriter> consumer);

}
