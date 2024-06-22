using DotNetty.Buffers;
using Tachyon.Nbt;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationRegistryPacket(
    string RegistryId,
    IList<ServerConfigurationRegistryPacket.Entry> Entries
    ) : IServerPacket 
{
    

    public record Entry(string id, NbtCompound? data)
    {
    }


    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.STRING, RegistryId);
        writer.WriteCollection(BinaryBuffer.REGISTRY_ENTRY, Entries);
    }
}
