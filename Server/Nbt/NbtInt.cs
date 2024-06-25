using Tachyon.Nbt;

namespace Server.Nbt;

public class NbtInt(int value) : Nbt
{
    
    public int Value { get; set; } 
    public override NbtType Type => NbtType.Int;
}