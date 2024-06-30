using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationTransferPacket(string Host, int Port) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.STRING, Host);
        writer.Write(BinaryBuffer.VAR_INT, Port);
    }
}