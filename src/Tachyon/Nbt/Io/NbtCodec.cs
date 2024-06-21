using DotNetty.Buffers;

namespace Tachyon.Nbt.Io;

// TODO: Json <-> Nbt conversion
public static class NbtCodec
{

    public static readonly NbtIo<IByteBuffer, IByteBuffer> DEFAULT_IO = new DefaultNbtIo();



    public static Nbt? ByteBufToNbt(IByteBuffer buffer)
    {
        NbtLimiter limiter = new NbtLimiter(buffer);
        return DEFAULT_IO.DeserializeNbt(limiter, buffer, false);
    }

    public static void NbtToByteBuff(Nbt nbt, IByteBuffer buffer)
    {
        DEFAULT_IO.SerializeNbt(buffer, nbt, false);
    }
    
    
}