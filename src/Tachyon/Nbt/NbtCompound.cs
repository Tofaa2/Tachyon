namespace Tachyon.Nbt;

public class NbtCompound : Nbt
{
    
    public readonly Dictionary<string, Nbt> Value = new();
    
    public override NbtType Type => NbtType.Compound;
}