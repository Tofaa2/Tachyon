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


    public void Write(IByteBuffer writer)
    {
        writer.WriteStr(RegistryId);
        writer.WriteArray(Entries.Count, Entries, (buffer, entry) =>
        {
            buffer.WriteStr(entry.id);
            buffer.WriteOptional(entry.data, (b, n) => b.WriteNbt(n));
        });
    }
}
