namespace Tachyon.Nbt;

public class NbtShort(short value) : Nbt
{
    
    public short Value { get; set; }
    public override NbtType Type => NbtType.Short;
}