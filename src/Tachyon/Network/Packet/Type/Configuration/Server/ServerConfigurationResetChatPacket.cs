using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationResetChatPacket() : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        
    }
}