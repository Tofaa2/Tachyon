using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Client;

public class ClientConfigurationAcknowledgeFinishPacket : IClientPacket
{
    public void Read(BinaryBuffer reader)
    {
        // Do nothing
    }
}