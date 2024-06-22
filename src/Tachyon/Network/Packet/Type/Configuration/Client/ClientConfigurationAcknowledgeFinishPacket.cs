using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Configuration.Client;

public class ClientConfigurationAcknowledgeFinishPacket : IClientPacket
{
    public void Read(BinaryBuffer reader)
    {
        // Do nothing
    }
}