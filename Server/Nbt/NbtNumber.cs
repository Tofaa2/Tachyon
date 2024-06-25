using System.Numerics;

namespace Server.Nbt;

public abstract class NbtNumber<T> : Nbt where T : unmanaged, INumber<T>
{
    
}