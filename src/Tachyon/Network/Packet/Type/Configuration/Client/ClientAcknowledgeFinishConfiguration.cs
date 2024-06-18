using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Configuration.Client;

public class ClientAcknowledgeFinishConfiguration : IClientPacket
{
    public void Read(IByteBuffer reader)
    {
        // Do nothing
    }
}