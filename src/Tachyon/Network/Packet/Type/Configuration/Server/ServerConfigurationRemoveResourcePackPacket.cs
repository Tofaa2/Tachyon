using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationRemoveResourcePackPacket(Guid? Uuid) : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteOptional(Uuid, (b, g) =>
        {
            Guid guid = (Guid)g!;
            b.WriteUUID(guid);
        });
    }
}