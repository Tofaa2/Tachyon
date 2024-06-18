using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationFinishPacket : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        // Do nothing
    }
}