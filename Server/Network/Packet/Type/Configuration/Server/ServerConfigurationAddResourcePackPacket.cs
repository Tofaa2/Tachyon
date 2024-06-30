using Server.Network.Binary;
using Server.Resourcepack;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationAddResourcePackPacket(ResourcePack Pack) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.RESOURCEPACK, Pack);
    }
}