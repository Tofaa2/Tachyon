namespace Tachyon.Nbt;

public class NbtLong(long value) : NbtNumber<long>
{
    
    public long Value { get; set; }
    public override NbtType Type => NbtType.Long;
}