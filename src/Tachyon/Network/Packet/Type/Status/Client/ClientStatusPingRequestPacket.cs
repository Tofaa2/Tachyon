using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Status.Client;

public class ClientStatusPingRequestPacket : IClientPacket
{

    public long Payload { get; private set; }
    
    public void Read(IByteBuffer reader)
    {
        Payload = reader.ReadLong();
    }
}