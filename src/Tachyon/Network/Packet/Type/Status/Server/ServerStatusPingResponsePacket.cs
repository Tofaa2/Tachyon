using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Status.Server;

public record ServerStatusPingResponsePacket(long Payload) : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteLong(Payload);
    }
}