using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationPingPacket(int Id) : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteInt(Id);
    }
}