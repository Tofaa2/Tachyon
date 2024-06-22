using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationFinishPacket : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        // Do nothing
    }
}