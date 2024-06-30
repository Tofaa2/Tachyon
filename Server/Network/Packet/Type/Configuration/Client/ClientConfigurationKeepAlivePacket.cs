using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Client;

public class ClientConfigurationKeepAlivePacket : IClientPacket
{

    public long Id { get; private set; }
    
    public void Read(BinaryBuffer buffer)
    {
        Id = buffer.Read(BinaryBuffer.LONG);
    }
}