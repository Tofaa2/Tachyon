using Tachyon.Nbt;

namespace Server.Nbt;

public class NbtFloat(float value) : Nbt
{
    
    public float Value { get; set; } 
    public override NbtType Type => NbtType.Float;
}