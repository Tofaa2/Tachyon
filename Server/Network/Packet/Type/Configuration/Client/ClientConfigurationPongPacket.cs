using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Client;

public class ClientConfigurationPongPacket : IClientPacket
{
    public int Id { get; private set; }
    
    public void Read(BinaryBuffer buffer)
    {
        Id = buffer.Read(BinaryBuffer.INT);
    }
}