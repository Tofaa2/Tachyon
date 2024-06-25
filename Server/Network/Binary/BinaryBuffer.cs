using DotNetty.Buffers;
using Server.Chat.Text;
using Server.Namespace;
using Server.Network.Packet.Type.Configuration.Server;
using Server.Position;
using Byte = Server.Network.Binary.Byte;
using Double = Server.Network.Binary.Double;
using String = Server.Network.Binary.String;

namespace Server.Network.Binary;

public class BinaryBuffer(IByteBuffer buffer)
{
    
    public static readonly IType<int> INT = new Int();
    public static readonly IType<short> SHORT = new Short();
    public static readonly IType<long> LONG = new Long();
    public static readonly IType<ushort> USHORT = new UShort();
    public static readonly IType<double> DOUBLE = new Double();
    public static readonly IType<float> FLOAT = new Float();
    public static readonly IType<byte> BYTE = new Byte();
    public static readonly IType<bool> BOOL = new Bool();
    public static readonly IType<string> STRING = new String();
    public static readonly IType<int> VAR_INT = new VarInt();
    public static readonly IType<long> VAR_LONG = new VarLong();
    public static readonly IType<ICoordinate> BLOCK_POSITION = new BlockPosition();
    public static readonly IType<Guid> UUID = new Uuid();
    public static readonly IType<global::Server.Nbt.Nbt> NBT = new NBT();
    public static readonly IType<NamespaceId> NAMESPACE_ID = new global::Server.Network.Binary.Namespace();
    public static readonly IType<IComponent> TEXT_COMPONENT = new Component();
    public static readonly IType<ServerConfigurationRegistryPacket.Entry> REGISTRY_ENTRY = new RegistryEntry();
    public static readonly IType<byte[]> BYTE_ARRAY = new ByteArray();
    public static readonly IType<byte[]> RAW_BYTES = new RawBytes();
    
    public readonly IByteBuffer Buffer = buffer;

    public BinaryBuffer(int capacity) : this(Unpooled.Buffer(capacity)) {}
    
    public BinaryBuffer(byte[] data) : this(Unpooled.WrappedBuffer(data)) {}
    
    public BinaryBuffer(byte[] data, int offset, int length) : this(Unpooled.WrappedBuffer(data, offset, length)) {}
    
    public int Capacity => Buffer.Capacity;
    public int ReaderIndex => Buffer.ReaderIndex;
    public int WriterIndex => Buffer.WriterIndex;
    
    public void WriteEnum<T>(T value)  where T : Enum {
        Write(VAR_INT, Convert.ToInt32(value));
    }
    
    public T ReadEnum<T>() where T : Enum {
        return (T) Enum.ToObject(typeof(T), Read(VAR_INT));
    }

    public byte[] ReadBytes(int length)
    {
        return Buffer.ReadBytes(length).Array;
    } 
    
    public void WriteCollection<T>(IType<T> type, ICollection<T> collection)
    {
        Write(VAR_INT, collection.Count);
        foreach (T value in collection)
        {
            Write(type, value);
        }
    }

    public ICollection<T> ReadCollection<T>(IType<T> type, int max = short.MaxValue)
    {
        int count = Read(VAR_INT);
        if (count <= 0)
        {
            throw new ArgumentOutOfRangeException($"Collection size {count} is less than 1");
        }
        if (count > max)
        {
            throw new ArgumentOutOfRangeException($"Collection size {count} exceeds maximum {max}");
        }
        List<T> collection = new List<T>(count);
        for (int i = 0; i < count; i++)
        {
            collection.Add(Read(type));
        }

        return collection;
    }
    
    public void Write<T>(IType<T> type, T value)
    {
        type.Write(this, value);
    }
    
    public T Read<T>(IType<T> type)
    {
        return type.Read(this);
    }

    public void WriteOptional<T>(IType<T?> type, T? value)
    {
        Write(BOOL, value != null);
        if (value != null)
        {
            Write(type, value);
        }
    }
    
    public T? ReadOptional<T>(IType<T> type)
    {
        return Read(BOOL) ? Read(type) : default;
    }
    
    public byte[] ToArray()
    {
        byte[] data = new byte[Buffer.ReadableBytes];
        Buffer.ReadBytes(data);
        return data;
    }
    
    
    public interface IType<T>
    {

        T Read(BinaryBuffer buffer);
        
        void Write(BinaryBuffer buffer, T value);

    }

}