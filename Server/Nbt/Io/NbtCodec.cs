using System.Numerics;
using System.Text.Json;
using System.Text.Json.Nodes;
using DotNetty.Buffers;
using Server.Nbt;

namespace Tachyon.Nbt.Io;

// TODO: Json <-> Nbt conversion
public static class NbtCodec
{

    public static readonly NbtIo<IByteBuffer, IByteBuffer> DEFAULT_IO = new DefaultNbtIo();
    
    public static Server.Nbt.Nbt? ByteBufToNbt(IByteBuffer buffer)
    {
        NbtLimiter limiter = new NbtLimiter(buffer);
        return DEFAULT_IO.DeserializeNbt(limiter, buffer, false);
    }

    public static void NbtToByteBuff(Server.Nbt.Nbt nbt, IByteBuffer buffer)
    {
        DEFAULT_IO.SerializeNbt(buffer, nbt, false);
    }
    
    
}