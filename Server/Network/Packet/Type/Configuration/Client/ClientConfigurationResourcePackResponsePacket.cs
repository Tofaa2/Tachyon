using Server.Network.Binary;
using Server.Resourcepack;

namespace Server.Network.Packet.Type.Configuration.Client;

public class ClientConfigurationResourcePackResponsePacket : IClientPacket
{
    
    public Guid ResourcePackId { get; private set; }
    public ResourcePackResult Result { get; private set; }
    
    public void Read(BinaryBuffer buffer)
    {
        ResourcePackId = buffer.Read(BinaryBuffer.UUID);
        Result = buffer.ReadEnum<ResourcePackResult>();
    }
}