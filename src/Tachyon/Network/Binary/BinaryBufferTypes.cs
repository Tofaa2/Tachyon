using DotNetty.Buffers;
using DotNetty.Codecs;
using Tachyon.Namespace;
using Tachyon.Nbt.Io;
using Tachyon.Nbt;
using Tachyon.Position;

namespace Tachyon.Network.Binary;

internal record ByteArray : BinaryBuffer.IType<byte[]>
{
    public byte[] Read(BinaryBuffer buffer)
    {
        var len = buffer.Read(BinaryBuffer.VAR_INT);
        if (len < 0) throw new DecoderException("The received encoded array length is less than zero! Weird array!");
        return buffer.Buffer.ReadAvailableBytes(len);
    }

    public void Write(BinaryBuffer buffer, byte[] value)
    {
        throw new NotImplementedException();
    }
}

internal record String : BinaryBuffer.IType<string>
{
    public string Read(BinaryBuffer buffer)
    {
        int length = buffer.Read(BinaryBuffer.VAR_INT);
        byte[] bytes = new byte[length];
        buffer.Buffer.ReadBytes(bytes);
        return System.Text.Encoding.UTF8.GetString(bytes);
    }

    public void Write(BinaryBuffer buffer, string value)
    {
        byte[] bytes = System.Text.Encoding.UTF8.GetBytes(value);
        buffer.Write(BinaryBuffer.VAR_INT, bytes.Length);
        buffer.Buffer.WriteBytes(bytes);
    }
}

internal record Uuid : BinaryBuffer.IType<Guid>
{
    public Guid Read(BinaryBuffer buffer)
    {
        long a = buffer.Buffer.ReadLong();
        long b = buffer.Buffer.ReadLong();
        byte[] guidData = new byte[16];
        Array.Copy(BitConverter.GetBytes(a), guidData, 8);
        Array.Copy(BitConverter.GetBytes(b), 0, guidData, 8, 8);
        return new Guid(guidData); // Thanks c#
    }

    public void Write(BinaryBuffer buffer, Guid value)
    {
        var bytes = value.ToByteArray();
        var long1 = BitConverter.ToInt64(bytes, 0);
        var long2 = BitConverter.ToInt64(bytes, 8);
        buffer.Buffer.WriteLong(long1);
        buffer.Buffer.WriteLong(long2);
    }
}

internal record BlockPosition : BinaryBuffer.IType<ICoordinate>
{
    public ICoordinate Read(BinaryBuffer buffer)
    {
        long value = buffer.Buffer.ReadLong(); 
        int x = (int) (value >> 38);
        int y = (int) (value << 52 >> 52); 
        int z = (int) (value << 26 >> 38);
        return new Point(x, y, z);    }

    public void Write(BinaryBuffer buffer, ICoordinate value)
    {
        int blockX = value.BlockX;
        int blockY = value.BlockY;
        int blockZ = value.BlockZ;
        long longPos = (((long) blockX & 0x3FFFFFF) << 38) |
                       (((long) blockZ & 0x3FFFFFF) << 12) |
                       ((long) blockY & 0xFFF);
        buffer.Buffer.WriteLong(longPos);
    }
}

internal record VarLong : BinaryBuffer.IType<long>
{
    public long Read(BinaryBuffer buffer)
    {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = buffer.Buffer.ReadByte();
            value |= (long) (currentByte & 0x7F) << position;

            if ((currentByte & 0x80) == 0) break;

            position += 7;

            if (position >= 64) throw new IOException("VarLong is too big");
        }

        return value;
    }

    public void Write(BinaryBuffer buffer, long value)
    {
        while (true) {
            if ((value & ~((long) 0x80)) == 0) {
                buffer.Buffer.WriteByte((byte)value);
                return;
            }

            buffer.Buffer.WriteByte((byte)(value & 0x7F) | 0x80);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }    }
}

internal record VarInt : BinaryBuffer.IType<int>
{
    public int Read(BinaryBuffer buffer)
    {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = buffer.Buffer.ReadByte();
            value |= (currentByte & 0x7F) << position;

            if ((currentByte & 0x80) == 0) break;

            position += 7;

            if (position >= 32) throw new IOException("VarInt is too big");
        }

        return value;
    }

    public void Write(BinaryBuffer buffer, int value)
    {
        while (true) {
            if ((value & ~0x7F) == 0) {
                buffer.Buffer.WriteByte(value);
                return;
            }

            buffer.Buffer.WriteByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
    }
}

internal record Namespace : BinaryBuffer.IType<NamespaceId>
{
    public NamespaceId Read(BinaryBuffer buffer)
    {
        return NamespaceId.FromString(buffer.Read(BinaryBuffer.STRING));
    }

    public void Write(BinaryBuffer buffer, NamespaceId value)
    {
        buffer.Write(BinaryBuffer.STRING, value.Full);
    }
}

internal record NBT : BinaryBuffer.IType<Nbt.Nbt>
{
    public Nbt.Nbt Read(BinaryBuffer buffer)
    {
        return NbtCodec.ByteBufToNbt(buffer.Buffer) ?? throw new Exception("Failed to read NBT");
    }

    public void Write(BinaryBuffer buffer, Nbt.Nbt value)
    {
        NbtCodec.NbtToByteBuff(value, buffer.Buffer);
    }
}

internal record Long : BinaryBuffer.IType<long>
{
    public long Read(BinaryBuffer buffer)
    {
        return buffer.Buffer.ReadLong();
    }

    public void Write(BinaryBuffer buffer, long value)
    {
        buffer.Buffer.WriteLong(value);
    }
}

internal record UShort : BinaryBuffer.IType<ushort>
{
    public ushort Read(BinaryBuffer buffer)
    {
        return buffer.Buffer.ReadUnsignedShort();
    }

    public void Write(BinaryBuffer buffer, ushort value)
    {
        buffer.Buffer.WriteUnsignedShort(value);
    }
}

internal record Short : BinaryBuffer.IType<short>
{
    public short Read(BinaryBuffer buffer)
    {
        return buffer.Buffer.ReadShort();
    }

    public void Write(BinaryBuffer buffer, short value)
    {
        buffer.Buffer.WriteShort(value);
    }
}

internal record Double : BinaryBuffer.IType<double>
{
    public double Read(BinaryBuffer buffer)
    {
        return buffer.Buffer.ReadDouble();
    }

    public void Write(BinaryBuffer buffer, double value)
    {
        buffer.Buffer.WriteDouble(value);
    }
}

internal record Float : BinaryBuffer.IType<float>
{
    public float Read(BinaryBuffer buffer)
    {
        return buffer.Buffer.ReadFloat();
    }

    public void Write(BinaryBuffer buffer, float value)
    {
        buffer.Buffer.WriteFloat(value);
    }
}

internal record Bool : BinaryBuffer.IType<bool>
{
    public bool Read(BinaryBuffer buffer)
    {
        return buffer.Buffer.ReadBoolean();
    }

    public void Write(BinaryBuffer buffer, bool value)
    {
        buffer.Buffer.WriteBoolean(value);
    }
}

internal record Byte : BinaryBuffer.IType<byte>
{
    public byte Read(BinaryBuffer buffer)
    {
        return buffer.Buffer.ReadByte();
    }

    public void Write(BinaryBuffer buffer, byte value)
    {
        buffer.Buffer.WriteByte(value);
    }
}

internal record Int : BinaryBuffer.IType<int>
{
    public int Read(BinaryBuffer buffer)
    {
        return buffer.Buffer.ReadInt();
    }

    public void Write(BinaryBuffer buffer, int value)
    {
        buffer.Buffer.WriteInt(value);
    }
}