using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationKeepAlivePacket(long Id) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.LONG, Id);
    }
}