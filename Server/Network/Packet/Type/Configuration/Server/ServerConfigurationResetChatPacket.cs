using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationResetChatPacket() : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        
    }
}