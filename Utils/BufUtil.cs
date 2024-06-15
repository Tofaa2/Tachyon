using DotNetty.Buffers;
using Tachyon.Network.Binary;
using Tachyon.Network.Netty;
using Tachyon.Network.Packet;

namespace Tachyon.Utils;

public static class BufUtil
{

    public static IByteBuffer createFramedPacket(IServerPacket packet, bool directbuffer)
    {
        var packetBuf = getPacketBuffer(packet);
        var framedBuf = directbuffer ? PooledByteBufferAllocator.Default.DirectBuffer() : Unpooled.Buffer();
        frameBuffer(packetBuf, framedBuf);
        packetBuf.Release();
        return framedBuf;
    }

    public static void frameBuffer(IByteBuffer packetBuffer, IByteBuffer frameTarget)
    {
        var packetSize = packetBuffer.ReadableBytes;
        var headerSize = getVarIntSize(packetSize);
        if (headerSize > 3)
        {
            throw new Exception("Unable to frame packet, header size is too big");
        }

        frameTarget.EnsureWritable(packetSize + headerSize);
        writeVarIntBuf(frameTarget, packetSize);
        frameTarget.WriteBytes(packetBuffer, packetBuffer.ReaderIndex, packetSize);
    }

    public static IByteBuffer writePacket(IServerPacket packet)
    {
        var buffer = getPacketBuffer(packet);
        var size = buffer.WriterIndex + 5 + 5;
        var newBuf = PooledByteBufferAllocator.Default.DirectBuffer(size);
        writePacket(newBuf, buffer, packet.Id);
        return newBuf;
    }

    public static void writePacket(IByteBuffer buffer, IServerPacket packet)
    {
        var packetbuffer = getPacketBuffer(packet);
        writePacket(buffer, packetbuffer, packet.Id);
    }

    public static void writePacket(IByteBuffer buffer, IByteBuffer packetbuffer, int packetid)
    {
        writeVarIntBuf(buffer, packetid);
        buffer.WriteBytes(packetbuffer);
        packetbuffer.Release();
    }

    public static IByteBuffer getPacketBuffer(IServerPacket packet)
    {
        IByteBuffer buffer = Unpooled.Buffer();
        NetworkBuffer packetBuffer = new NetworkBuffer(buffer);
        packet.Write(packetBuffer);
        return packetBuffer.Buffer;
    }
    
    public static int getVarIntSize(int input) {
        return (input & 0xFFFFFF80) == 0
            ? 1 : (input & 0xFFFFC000) == 0
                ? 2 : (input & 0xFFE00000) == 0
                    ? 3 : (input & 0xF0000000) == 0
                        ? 4 : 5;
    }

    public static void writeVarIntBuf(IByteBuffer buffer, int value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            buffer.WriteByte(temp);
        } while (value != 0);
    }

    public static void writeVarInt(IByteBuffer writer, int value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            writer.WriteByte(temp);
        } while (value != 0);
    }

    public static int readVarInt(IByteBuffer buffer) {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = buffer.ReadByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new Exception("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static void writeVarLong(IByteBuffer writer, long value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            writer.WriteByte(temp);
        } while (value != 0);
    }
    
}