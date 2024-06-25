using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationFinishPacket : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        // Do nothing
    }
}