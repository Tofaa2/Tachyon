using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Configuration.Client;

public class ClientConfigurationAcknowledgeFinishPacket : IClientPacket
{
    public void Read(IByteBuffer reader)
    {
        // Do nothing
    }
}