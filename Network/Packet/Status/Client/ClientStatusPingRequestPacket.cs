using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Status.Client;

public class ClientStatusPingRequestPacket : IClientPacket
{

    public long Payload;
    
    public void Read(NetworkBuffer reader)
    {
        Payload = reader.Read(NetworkBufferTypes.LONG);
    }

    public void Write(NetworkBuffer writer)
    {
        writer.Write(NetworkBufferTypes.LONG, Payload);
    }
}