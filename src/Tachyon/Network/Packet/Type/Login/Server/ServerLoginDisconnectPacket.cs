using DotNetty.Buffers;
using Tachyon.Chat.Text;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Server;

public record ServerLoginDisconnectPacket(IComponent Reason) : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteTextComponent(Reason);
    }
}