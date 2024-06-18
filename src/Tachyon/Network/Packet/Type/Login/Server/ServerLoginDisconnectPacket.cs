using DotNetty.Buffers;
using Tachyon.Network.Binary;
using Tachyon.Text;

namespace Tachyon.Network.Packet.Type.Login.Server;

public record ServerLoginDisconnectPacket(IComponent Reason) : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteTextComponent(Reason);
    }
}