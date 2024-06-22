using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Status.Client;

public class ClientStatusPingRequestPacket : IClientPacket
{

    public long Payload { get; private set; }
    
    public void Read(BinaryBuffer reader)
    {
        Payload = reader.Read(BinaryBuffer.LONG);
    }
}