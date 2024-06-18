using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationPluginMessagePacket(string Key, byte[] data) : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        throw new NotImplementedException();
    }
}