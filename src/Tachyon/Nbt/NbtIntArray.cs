namespace Tachyon.Nbt;

public class NbtIntArray(int[] value) : Nbt
{
    
    public int[] Value { get; set; } 
    public override NbtType Type => NbtType.IntArray;
}