package net.tachyon.utils.binary;

public final class BitmaskUtil {

    private BitmaskUtil() {}

    public static byte changeBit(byte value, byte mask, byte replacement, byte shift) {
        return (byte) (value & ~mask | (replacement << shift));
    }

}
