package net.tachyon.binary;


import io.netty.buffer.ByteBuf;
import net.kyori.adventure.text.Component;
import net.tachyon.coordinate.Point;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBT;
import org.jglrxavpok.hephaistos.nbt.NBTException;

import java.io.IOException;
import java.util.UUID;

/**
 * Class used to read from a byte array.
 * <p>
 * WARNING: not thread-safe.
 */
public interface BinaryReader {

    int readVarInt();

    boolean readBoolean();

    byte readByte();

    short readShort();

    char readChar();

    int readUnsignedShort();

    int readInteger();

    long readLong();

    float readFloat();

    double readDouble();

    @NotNull ItemStack readSlot();

    int available();

    /**
     * Reads a string size by a var-int.
     * <p>
     * If the string length is higher than {@code maxLength},
     * the code throws an exception and the string bytes are not read.
     *
     * @param maxLength the max length of the string
     * @return the string
     * @throws IllegalStateException if the string length is higher than {@code maxLength}
     */
    @NotNull String readSizedString(int maxLength);

    byte[] readBytes(int length);

    String[] readSizedStringArray(int maxLength);

    int[] readVarIntArray();

    byte[] getRemainingBytes();

    Point readPoint();

    UUID readUuid();

    NBT readTag() throws NBTException, IOException;

    Component readComponent(int maxLength);

    @NotNull ByteBuf getBuffer();

}
