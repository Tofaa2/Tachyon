using DotNetty.Buffers;

namespace Server.Nbt;

public class NbtLimiter
{

    private readonly int max;
    private IByteBuffer? buffer;
    private int bytes;
    
    public NbtLimiter(int max)
    {
        this.max = max;
    }

    public NbtLimiter(IByteBuffer buffer)
    {
        this.buffer = buffer;
        this.max = 2097152;
    }

    public NbtLimiter()
    {
        this.max = int.MaxValue;
    }

    public void Increment(int amount)
    {
        bytes += amount;
        if (bytes > max)
        {
            throw new Exception("NBT tag is too large");
        }
    }

    public void CheckReadability(int len)
    {
        if (buffer != null)
        {
            
        }
    }
    
}