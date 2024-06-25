using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationPingPacket(int Id) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.INT, Id);
    }
}