using Server.Network.Binary;

namespace Server.Network.Packet.Type.Status.Client;

public class ClientStatusPingRequestPacket : IClientPacket
{

    public long Payload { get; private set; }
    
    public void Read(BinaryBuffer reader)
    {
        Payload = reader.Read(BinaryBuffer.LONG);
    }
}