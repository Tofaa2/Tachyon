using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationPluginMessagePacket(string Key, byte[] data) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        throw new NotImplementedException();
    }
}