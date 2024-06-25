using Tachyon.Nbt;

namespace Server.Nbt;

public class NbtByteArray(byte[] value) : Nbt
{
    
    public byte[] Value { get; set; } = value;
    public override NbtType Type => NbtType.ByteArray;
}