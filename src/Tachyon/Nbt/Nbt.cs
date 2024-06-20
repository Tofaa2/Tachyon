using System.Text.Json;

namespace Tachyon.Nbt;

public abstract class Nbt
{

    public abstract NbtType Type { get; }

}