using System.Text;
using DotNetty.Buffers;
using DotNetty.Codecs;

namespace Tachyon.Network.Binary;

/** Static helper extensions for networking. */
public static class NetworkBuffer
{
    private static readonly int SegmentBits = 0x7F;
    private static readonly int ContinueBit = 0x80;

    public static int Write3EmptyBytes(this IByteBuffer buffer)
    {
        var index = buffer.WriterIndex;
        buffer.WriteMedium(0);
        return index;
    }

    public static void Write3ByteVarInt(this IByteBuffer buffer, int startIndex, int value)
    {
        var originalIndex = buffer.WriterIndex;
        buffer.SetWriterIndex(startIndex);
        var encoded = (value & 0x7F) | 0x80 << 16 | ((value >> 7) & 0x7F | 0x80 << 8) | (value >> 14);
        buffer.WriteMedium(encoded);
        buffer.SetWriterIndex(originalIndex);
    }

    public static T ReadEnum<T>(this IByteBuffer buffer) where T : Enum
    {
        var values = Enum.GetValues(typeof(T));
        var index = buffer.ReadVarInt();
        return (T) values.GetValue(index);
    }
    
    public static void WriteEnum<T>(this IByteBuffer buffer, T value) where T : Enum
    {
        buffer.WriteVarInt(Convert.ToInt32(value));
    }
     
    public static string ReadStr(this IByteBuffer buffer, int maxLength)
    {
        var length = buffer.ReadVarInt();
        if (length < 0) throw new DecoderException("The received encoded string length is less than zero! Weird string!");
        if (length > maxLength * 4) throw new DecoderException("The received string length is longer than maximum allowed (" + length + " > " + maxLength * 4 + ")");
        var str = Encoding.UTF8.GetString(buffer.ReadAvailableBytes(length));
        if (str.Length > maxLength) throw new DecoderException("The received string length is longer than maximum allowed (" + length + " > " + maxLength + ")");
        return str;
    }

    public static string ReadStr(this IByteBuffer buffer)
    {
        return buffer.ReadStr(short.MaxValue);
    }

    public static void WriteStr(this IByteBuffer buffer, string value, int maxLength)
    {
        var bytes = Encoding.UTF8.GetBytes(value);
        if (bytes.Length > maxLength) throw new EncoderException("String too big (was " + bytes.Length + " bytes encoded, max " + maxLength + ")");
        buffer.WriteVarInt(bytes.Length);
        buffer.WriteBytes(bytes);
    }
    
    public static void WriteStr(this IByteBuffer buffer, string value)
    {
        buffer.WriteStr(value, short.MaxValue);
    }

    public static byte[] ReadVarIntByteArray(this IByteBuffer buffer)
    {
        return buffer.ReadAvailableBytes(buffer.ReadVarInt());
    }

    public static byte[] ReadVarIntByteArray(this IByteBuffer buffer, int maxLen)
    {
        var length = buffer.ReadVarInt();
        if (length < 0) throw new DecoderException("The received encoded array length is less than zero! Weird array!");
        return buffer.ReadAvailableBytes(length);
    }

    public static void WriteVarIntByteArray(this IByteBuffer buffer, byte[] value)
    {
        buffer.WriteVarInt(value.Length);
        buffer.WriteBytes(value);
    }

    public static void WriteLongArray(this IByteBuffer buffer, long[] value)
    {
        buffer.WriteVarInt(value.Length);
        for (var i = 0; i < value.Length; i++) buffer.WriteLong(value[i]);
    }

    public static long[] ReadLongArray(this IByteBuffer buffer)
    {
        var length = buffer.ReadVarInt();
        var value = new long[length];
        for (var i = 0; i < length; i++) value[i] = buffer.ReadLong();
        return value;
    }

    public static Guid ReadUUID(this IByteBuffer buffer)
    {
        long a = buffer.ReadLong();
        long b = buffer.ReadLong();
        byte[] guidData = new byte[16];
        Array.Copy(BitConverter.GetBytes(a), guidData, 8);
        Array.Copy(BitConverter.GetBytes(b), 0, guidData, 8, 8);
        return new Guid(guidData); // Thanks c#
    }

    public static void WriteUUID(this IByteBuffer buffer, Guid uuid)
    {
        var bytes = uuid.ToByteArray();
        var long1 = BitConverter.ToInt64(bytes, 0);
        var long2 = BitConverter.ToInt64(bytes, 8);
        buffer.WriteLong(long1);
        buffer.WriteLong(long2);
    }

    public static byte[] ReadAvailableBytes(this IByteBuffer buffer, int length)
    {
        var bytes = new byte[length];
        buffer.ReadBytes(bytes);
        return bytes;
    }
    
    public static long ReadVarLong(this IByteBuffer buffer)
    {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = buffer.ReadByte();
            value |= (long) (currentByte & SegmentBits) << position;

            if ((currentByte & ContinueBit) == 0) break;

            position += 7;

            if (position >= 64) throw new IOException("VarLong is too big");
        }

        return value;
    }

    public static void WriteVarLong(this IByteBuffer buffer, long value)
    {
        while (true) {
            if ((value & ~((long) SegmentBits)) == 0) {
                buffer.WriteByte((byte)value);
                return;
            }

            buffer.WriteByte((byte)(value & SegmentBits) | ContinueBit);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }
    
    public static int ReadVarInt(this IByteBuffer buffer)
    {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = buffer.ReadByte();
            value |= (currentByte & SegmentBits) << position;

            if ((currentByte & ContinueBit) == 0) break;

            position += 7;

            if (position >= 32) throw new IOException("VarInt is too big");
        }

        return value;
    }

    public static void WriteVarInt(this IByteBuffer buffer, int value)
    {
        while (true) {
            if ((value & ~SegmentBits) == 0) {
                buffer.WriteByte(value);
                return;
            }

            buffer.WriteByte((value & SegmentBits) | ContinueBit);
            value >>>= 7;
        }
    }
    
}