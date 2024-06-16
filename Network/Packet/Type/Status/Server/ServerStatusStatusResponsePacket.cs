using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Status.Server;

public record ServerStatusStatusResponsePacket(string JsonResponse) : IPacket
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteStr(JsonResponse);
    }
}