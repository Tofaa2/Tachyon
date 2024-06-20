using System.Text.Json;

namespace Tachyon.Nbt;

public class NbtByte(byte value) : NbtNumber<byte>
{
    public override NbtType Type => NbtType.Byte;
    
    public byte Value { get; set; } = value;
    
    public sbyte SignedValue => (sbyte) Value;
}