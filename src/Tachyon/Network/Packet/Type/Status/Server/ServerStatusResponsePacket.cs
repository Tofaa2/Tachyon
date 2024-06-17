using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Status.Server;

public record ServerStatusResponsePacket(string JsonPayload) : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteStr(JsonPayload);
    }
}