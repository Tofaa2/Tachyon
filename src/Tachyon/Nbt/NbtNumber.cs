using System.Numerics;

namespace Tachyon.Nbt;

public abstract class NbtNumber<T> : Nbt where T : unmanaged, INumber<T>
{
    
}