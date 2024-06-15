using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Status.Client;

public class ClientStatusRequestPacket : IClientPacket
{
    public void Read(NetworkBuffer reader)
    {
        // Do nothing;
    }

    public void Write(NetworkBuffer writer)
    {
        // Do nothing
    }
    
}