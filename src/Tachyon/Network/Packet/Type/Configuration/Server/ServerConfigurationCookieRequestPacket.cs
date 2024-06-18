using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationCookieRequestPacket(string Key) : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteStr(Key);
    }
}