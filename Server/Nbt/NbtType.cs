namespace Server.Nbt;

public static class NbtTypeExtensions
{

    public static Type AsNbtClass(this NbtType type)
    {
        Type nbtClass;
        switch (type)
        {
            case NbtType.Byte:
                nbtClass = typeof(NbtByte);
                break;
            case NbtType.Compound:
                nbtClass = typeof(NbtCompound);
                break;
            case NbtType.Double:
                nbtClass = typeof(NbtDouble);
                break;
            case NbtType.Float:
                nbtClass = typeof(NbtFloat);
                break;
            case NbtType.Int:
                nbtClass = typeof(NbtInt);
                break;
            case NbtType.List:
                nbtClass = typeof(NbtList<>);
                break;
            case NbtType.Long:
                nbtClass = typeof(NbtLong);
                break;
            case NbtType.Short:
                nbtClass = typeof(NbtShort);
                break;
            case NbtType.String:
                nbtClass = typeof(NbtString);
                break;
            case NbtType.ByteArray:
                nbtClass = typeof(NbtByteArray);
                break;
            case NbtType.IntArray:
                nbtClass = typeof(NbtIntArray);
                break;
            case NbtType.LongArray:
                nbtClass = typeof(NbtLongArray);
                break;
            default:
                nbtClass = typeof(NbtEnd);
                break;
        }
        return nbtClass;
    }
    
    
}

public enum NbtType : byte
{
    
    End = 0x00,
    Byte = 0x01,
    Short = 0x02,
    Int = 0x03,
    Long = 0x04,
    Float = 0x05,
    Double = 0x06,
    ByteArray = 0x07,
    String = 0x08,
    List = 0x09,
    Compound = 0x0a,
    IntArray = 0x0b,
    LongArray = 0x0c
    
}