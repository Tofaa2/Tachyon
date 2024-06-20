namespace Tachyon.Nbt;

public class NbtString(string value) : Nbt
{
    
    public string Value { get; set; }
    public override NbtType Type => NbtType.String;
}