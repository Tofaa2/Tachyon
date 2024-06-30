using Server.Datapack;
using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationKnownDataPacksPacket(ICollection<KnownDataPack> KnownDataPacks) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.WriteCollection(BinaryBuffer.KNOWN_DATA_PACK, KnownDataPacks);
    }
}