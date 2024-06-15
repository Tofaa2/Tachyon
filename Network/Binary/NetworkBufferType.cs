using System.Net;
using System.Text;

namespace Tachyon.Network.Binary;

public static class NetworkBufferTypes
{
    public static readonly INetworkBufferType<int> INT = new Int();
    public static readonly INetworkBufferType<float> FLOAT = new Float();
    public static readonly INetworkBufferType<bool> BOOLEAN = new Boolean();
    public static readonly INetworkBufferType<double> DOUBLE = new Double();
    public static readonly INetworkBufferType<int> VAR_INT = new VarInt();
    public static readonly INetworkBufferType<long> LONG = new Long();
    public static readonly INetworkBufferType<short> SHORT = new Short();
    public static readonly INetworkBufferType<ushort> USHORT = new UShort();
    public static readonly INetworkBufferType<long> VAR_LONG = new VarLong();
    public static readonly INetworkBufferType<string> STRING = new String();
    public static readonly INetworkBufferType<Guid> UUID = new UUID();
    public static readonly INetworkBufferType<byte> BYTE = new Byte();
}

public interface  INetworkBufferType<T>
{

    T Read(NetworkBuffer reader);

    void Write(NetworkBuffer writer, T value);

}

internal record Int : INetworkBufferType<int>
{
    public int Read(NetworkBuffer reader)
    {
        return reader.Buffer.ReadInt();
    }

    public void Write(NetworkBuffer writer, int value)
    {
        writer.Buffer.WriteInt(value);
    }
}
internal record Float : INetworkBufferType<float>
{
    public float Read(NetworkBuffer reader)
    {
        return reader.Buffer.ReadFloat();
    }

    public void Write(NetworkBuffer writer, float value)
    {
        writer.Buffer.WriteFloat(value);
    }
}
internal record Short : INetworkBufferType<short>
{
    public short Read(NetworkBuffer reader)
    {
        return reader.Buffer.ReadShort();
    }

    public void Write(NetworkBuffer writer, short value)
    {
        writer.Buffer.WriteShort(value);
    }
}
internal record UShort : INetworkBufferType<ushort>
{
    public ushort Read(NetworkBuffer reader)
    {
        return reader.Buffer.ReadUnsignedShort();
    }

    public void Write(NetworkBuffer writer, ushort value)
    {
        writer.Buffer.WriteUnsignedShort(value);
    }
}

internal record String : INetworkBufferType<string>
{
    public string Read(NetworkBuffer reader)
    {
        int length = reader.Read(NetworkBufferTypes.VAR_INT);
        var data = reader.Buffer.ReadBytes(length);
        return Encoding.UTF8.GetString(data.Array);
    }

    public void Write(NetworkBuffer writer, string value)
    {
        var data = Encoding.UTF8.GetBytes(value);
        writer.Write(NetworkBufferTypes.VAR_INT, data.Length);
        writer.Buffer.WriteBytes(data);
    }
}
internal record Long : INetworkBufferType<long>
{
    public long Read(NetworkBuffer reader)
    {
        return reader.Buffer.ReadLong();
    }

    public void Write(NetworkBuffer writer, long value)
    {
        writer.Buffer.WriteLong(value);
    }
}
internal record VarInt : INetworkBufferType<int>
{
    public int Read(NetworkBuffer reader)
    {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = (byte)reader.Buffer.ReadByte();
            value |= (currentByte & 0x7F) << position;

            if ((currentByte & 0x80) == 0) break;

            position += 7;

            if (position >= 32) throw new Exception("VarInt is too big");
        }
        return value;
    }

    public void Write(NetworkBuffer buf, int value)
    {
        var write = 0;
        do
        {
            var temp = (byte)(value & 127);
            value >>= 7;
            if (value != 0)
            {
                temp |= 128;
            }
            buf.Buffer.WriteByte(temp);
            write++;
        } while (value != 0);
    }
}

internal record VarLong : INetworkBufferType<long>
{
    public long Read(NetworkBuffer reader)
    {
        var numRead = 0;
        long result = 0;
        byte read;
        do
        {
            read = (byte)reader.Buffer.ReadByte();
            var value = read & 0x7f;
            result |= (uint) (value << (7 * numRead));
            numRead++;
            if (numRead > 10)
            {
                throw new Exception("VarLong is too big");
            }
        } while ((read & 0x80) != 0);

        return result;
    }

    public void Write(NetworkBuffer writer, long value)
    {
        var write = 0;
        do
        {
            var temp = (byte)(value & 127);
            value >>= 7;
            if (value != 0)
            {
                temp |= 128;
            }
            writer.Buffer.WriteByte(temp);
            write++;
        } while (value != 0);
    }
}
internal record Boolean : INetworkBufferType<bool>
{
    public bool Read(NetworkBuffer reader)
    {
        return reader.Buffer.ReadBoolean();
    }

    public void Write(NetworkBuffer writer, bool value)
    {
        writer.Buffer.WriteBoolean(value);
    }
}

internal record Byte : INetworkBufferType<byte>
{
    public byte Read(NetworkBuffer reader)
    {
        return reader.Buffer.ReadByte();
    }

    public void Write(NetworkBuffer writer, byte value)
    {
        writer.Buffer.WriteByte(value);
    }
}

internal record UUID : INetworkBufferType<Guid>
{
    public Guid Read(NetworkBuffer reader)
    {
        var long1 = reader.Buffer.ReadLong();
        var long2 = reader.Buffer.ReadLong();
        byte[] guidData = new byte[16];
        Array.Copy(BitConverter.GetBytes(long1), guidData, 8);
        Array.Copy(BitConverter.GetBytes(long2), 0, guidData, 8, 8);
        return new Guid(guidData);
    }

    public void Write(NetworkBuffer writer, Guid value)
    {
        var bytes = value.ToByteArray();
        var long1 = BitConverter.ToInt64(bytes, 0);
        var long2 = BitConverter.ToInt64(bytes, 8);
        writer.Buffer.WriteLong(long1);
        writer.Buffer.WriteLong(long2);
    }
}
internal record Double : INetworkBufferType<double>
{
    public double Read(NetworkBuffer reader)
    {
       return reader.Buffer.ReadDouble();
    }

    public void Write(NetworkBuffer writer, double value)
    {
        writer.Buffer.WriteDouble(value);
    }
}