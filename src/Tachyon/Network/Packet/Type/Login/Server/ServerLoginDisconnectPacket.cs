using DotNetty.Buffers;
using Tachyon.Chat.Text;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Server;

public record ServerLoginDisconnectPacket(IComponent Reason) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.TEXT_COMPONENT, Reason);
    }
}