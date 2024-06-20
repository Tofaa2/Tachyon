namespace Tachyon.Nbt;

public class NbtEnd : Nbt
{
    
    public static readonly NbtEnd Instance = new NbtEnd();
    
    public override NbtType Type => NbtType.End;
}