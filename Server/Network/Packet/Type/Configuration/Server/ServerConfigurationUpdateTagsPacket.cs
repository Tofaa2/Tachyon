using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationUpdateTagsPacket() : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        throw new NotImplementedException();
    }
}