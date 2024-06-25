using Tachyon.Nbt;

namespace Server.Nbt;

public class NbtLongArray(long[] value) : Nbt
{
    
    
    public long[] Value { get; set; }
    public override NbtType Type => NbtType.LongArray;
}