using DotNetty.Buffers;
using Tachyon.Network.Binary;
using Tachyon.Network.Packet;

namespace Tachyon.Utils;

public static class PacketUtil
{

    public static void WritePacket(IByteBuffer buffer, IPacket packet)
    {
        var packetBuffer = GetPacketBuffer(packet);
        WritePacket(buffer, packetBuffer, PacketRegistry.GetServerPacketId(packet.GetType()));
    }
    
    public static void WritePacket(IByteBuffer buffer, IByteBuffer packetBuffer, int packetId)
    {
        buffer.WriteVarInt(packetId);
        buffer.WriteBytes(packetBuffer);
        packetBuffer.Release();
    }

    public static IByteBuffer GetPacketBuffer(IPacket packet)
    {
        var buffer = PooledByteBufferAllocator.Default.HeapBuffer();
        packet.Write(buffer);
        return buffer;
    }
    
}