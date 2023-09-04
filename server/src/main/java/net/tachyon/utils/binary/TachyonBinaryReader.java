package net.tachyon.utils.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.kyori.adventure.text.Component;
import net.tachyon.binary.BinaryReader;
import net.tachyon.chat.Adventure;
import net.tachyon.coordinate.Point;
import net.tachyon.item.ItemStack;
import net.tachyon.utils.NBTUtils;
import net.tachyon.utils.SerializerUtils;
import net.tachyon.utils.Utils;
import net.tachyon.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBT;
import org.jglrxavpok.hephaistos.nbt.NBTException;
import org.jglrxavpok.hephaistos.nbt.NBTReader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * {@inheritDoc}
 */
public class TachyonBinaryReader extends InputStream implements BinaryReader {

    private final ByteBuf buffer;
    private final NBTReader nbtReader = new NBTReader(this, false);

    public TachyonBinaryReader(@NotNull ByteBuf buffer) {
        this.buffer = buffer;
    }

    public TachyonBinaryReader(byte[] bytes) {
        this(Unpooled.wrappedBuffer(bytes));
    }

    public int readVarInt() {
        return Utils.readVarInt(buffer);
    }

    public boolean readBoolean() {
        return buffer.readBoolean();
    }

    public byte readByte() {
        return buffer.readByte();
    }

    public short readShort() {
        return buffer.readShort();
    }

    public char readChar() {
        return buffer.readChar();
    }

    public int readUnsignedShort() {
        return buffer.readUnsignedShort();
    }

    public int readInteger() {
        return buffer.readInt();
    }

    public long readLong() {
        return buffer.readLong();
    }

    public float readFloat() {
        return buffer.readFloat();
    }

    public double readDouble() {
        return buffer.readDouble();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String readSizedString(int maxLength) {
        final int length = readVarInt();
        Check.stateCondition(length > maxLength,
                "String length (" + length + ") was higher than the max length of " + maxLength);

        final byte[] bytes = readBytes(length);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public byte[] readBytes(int length) {
        ByteBuf buf = buffer.readBytes(length);
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        buf.release();
        return bytes;
    }

    public String[] readSizedStringArray(int maxLength) {
        final int size = readVarInt();
        String[] strings = new String[size];
        for (int i = 0; i < size; i++) {
            strings[i] = readSizedString(maxLength);
        }
        return strings;
    }

    public int[] readVarIntArray() {
        final int size = readVarInt();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = readVarInt();
        }
        return array;
    }

    public byte[] getRemainingBytes() {
        return readBytes(buffer.readableBytes());
    }

    public Point readPoint() {
        final long value = buffer.readLong();
        return SerializerUtils.longToBlockPosition(value);
    }

    public UUID readUuid() {
        final long most = readLong();
        final long least = readLong();
        return new UUID(most, least);
    }

    /**
     * Tries to read an {@link TachyonItemStack}.
     *
     * @return the read item
     * @throws NullPointerException if the item could not get read
     */
    @NotNull
    public ItemStack readSlot() {
        final ItemStack itemStack = NBTUtils.readItemStack(this);
        Check.notNull(itemStack, "#readSlot returned null, probably because the buffer was corrupted");
        return itemStack;
    }

    public Component readComponent(int maxLength) {
        final String string = readSizedString(maxLength);
        return Adventure.COMPONENT_SERIALIZER.deserialize(string);
    }

    @NotNull
    public ByteBuf getBuffer() {
        return buffer;
    }

    @Override
    public int read() {
        return readByte() & 0xFF;
    }

    @Override
    public int available() {
        return buffer.readableBytes();
    }

    public NBT readTag() throws IOException, NBTException {
        return nbtReader.read();
    }
}
