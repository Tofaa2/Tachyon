using Tachyon.Nbt;

namespace Server.Nbt;

public class NbtByte(byte value) : NbtNumber<byte>
{
    public override NbtType Type => NbtType.Byte;

    public NbtByte(bool value) : this (value ? (byte) 1 : (byte) 0)
    {
        
    }
    
    public byte Value { get; set; } = value;
    
    public sbyte SignedValue => (sbyte) Value;
}