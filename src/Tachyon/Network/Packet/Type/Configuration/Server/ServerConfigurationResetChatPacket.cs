using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationResetChatPacket() : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        
    }
}