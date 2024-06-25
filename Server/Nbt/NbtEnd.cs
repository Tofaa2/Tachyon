using Tachyon.Nbt;

namespace Server.Nbt;

public class NbtEnd : Nbt
{
    
    public static readonly NbtEnd Instance = new NbtEnd();
    private NbtEnd() {}
    public override NbtType Type => NbtType.End;
}