using Tachyon.Nbt;

namespace Server.Nbt;

public class NbtDouble(double value) : NbtNumber<double>
{
    
    public double Value { get; set; } = value;
    public override NbtType Type => NbtType.Double;
}