using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationRemoveResourcePackPacket(Guid? Uuid) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.WriteOptional(BinaryBuffer.UUID, (Guid)Uuid!);
    }
}