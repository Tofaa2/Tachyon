using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationPluginMessagePacket(string Key, byte[] data) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        throw new NotImplementedException();
    }
}