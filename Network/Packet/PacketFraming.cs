using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet;

public class PacketFraming
{

    public static IByteBuffer Frame(IPacket packet)
    {
        var buffer = Unpooled.DirectBuffer();
        WriteFramedPacket(buffer, packet);
        return buffer;
    }

    public static void WriteFramedPacket(IByteBuffer buffer, IPacket packet)
    {
        var packetLengthIndex = buffer.Write3EmptyBytes();
        var startIndex = buffer.WriterIndex;

        WritePacketUncompressed(buffer, packet);
        // if (compressionThreshold > 0) {
        //     writeCompressed(buf, packet)
        // } else {
        //     writePacket(buf, packet)
        // }
        var totalPacketLength = buffer.WriterIndex - startIndex;
        buffer.Write3ByteVarInt(packetLengthIndex, totalPacketLength);
    }

    private static void WritePacketUncompressed(IByteBuffer buffer, IPacket packet)
    {
        buffer.WriteVarInt(PacketRegistry.GetServerPacketId(packet.GetType()));
        packet.Write(buffer);
    }
    
}