using Server.Datapack;
using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Client;

public class ClientConfigurationKnownDataPacksPacket : IClientPacket
{

    public ICollection<KnownDataPack> KnownDataPacks { get; private set; }
    
    
    public void Read(BinaryBuffer buffer)
    {
        KnownDataPacks = buffer.ReadCollection(BinaryBuffer.KNOWN_DATA_PACK);
    }
}