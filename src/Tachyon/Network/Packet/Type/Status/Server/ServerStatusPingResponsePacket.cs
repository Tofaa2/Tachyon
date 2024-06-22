using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Status.Server;

public record ServerStatusPingResponsePacket(long Payload) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.LONG,Payload);
    }
}