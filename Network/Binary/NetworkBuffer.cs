using DotNetty.Buffers;

namespace Tachyon.Network.Binary;

public class NetworkBuffer
{

    public IByteBuffer Buffer { get; internal set; }
    
    
    
    public NetworkBuffer(IByteBuffer buffer)
    {
        Buffer = buffer;
    }

     public T Read<T>(INetworkBufferType<T> type)
    {
        return type.Read(this);
    }
    
    public void Write<T>(INetworkBufferType<T> type, T value)
    {
        type.Write(this, value);
    }
    
    public void WriteCollection<T>(INetworkBufferType<T> type, IEnumerable<T> collection, int sizeCap)
    {
        var enumerable = collection as T[] ?? collection.ToArray();
        int count = enumerable.Count();
        if (count > sizeCap)
        {
            throw new ArgumentOutOfRangeException(nameof(collection), $"Collection size is greater than {sizeCap}");
        }
        Write(NetworkBufferTypes.VAR_INT, count);
        foreach (var element in enumerable)
        {
            Write(type, element);
        }
    }
    
    public void WriteCollection<T>(INetworkBufferType<T> type, IEnumerable<T> collection)
    {
        WriteCollection(type, collection, int.MaxValue);
    }
    
    public void WriteEnum<T>(T value) where T : Enum
    {
        Write(NetworkBufferTypes.VAR_INT, Convert.ToInt32(value));
    }
    
    public void WriteOptional<T>(INetworkBufferType<T> type, T? value)
    {
        Write(NetworkBufferTypes.BOOLEAN, value != null);
        if (value != null)
        {
            Write(type, value);
        }
    }
    
    public IEnumerable<T> ReadCollection<T>(INetworkBufferType<T> type, int sizeCap)
    {
        var size = Read(NetworkBufferTypes.VAR_INT);
        if (size > sizeCap)
        {
            throw new ArgumentOutOfRangeException(nameof(size), $"Collection size is greater than {sizeCap}");
        }
        for (int i = 0; i < size; i++)
        {
            yield return Read(type);
        }
    }
    
    public IEnumerable<T> ReadCollection<T>(INetworkBufferType<T> type)
    {
        return ReadCollection(type, int.MaxValue);
    }
    
    public T ReadEnum<T>() where T : Enum
    {
        var values = Enum.GetValues(typeof(T));
        var value = Read(NetworkBufferTypes.INT);
        if (value < 0 || value >= values.Length)
        {
            throw new ArgumentOutOfRangeException(nameof(value), $"Value is out of range for enum {typeof(T).Name}");
        }
        return (T) values.GetValue(value);
    }
    
    public T? ReadOptional<T>(INetworkBufferType<T> type)
    {
        var exists = Read(NetworkBufferTypes.BOOLEAN);
        if (!exists) return default;
        return Read(type);
    }
    
}