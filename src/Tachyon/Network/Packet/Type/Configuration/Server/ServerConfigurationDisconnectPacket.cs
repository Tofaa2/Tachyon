using DotNetty.Buffers;
using Tachyon.Chat.Text;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationDisconnectPacket(IComponent Reason) : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteTextComponent(Reason);
    }
}