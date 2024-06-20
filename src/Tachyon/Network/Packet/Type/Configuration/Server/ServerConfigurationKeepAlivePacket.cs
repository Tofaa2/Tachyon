using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationKeepAlivePacket(long Id) : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteLong(Id);
    }
}