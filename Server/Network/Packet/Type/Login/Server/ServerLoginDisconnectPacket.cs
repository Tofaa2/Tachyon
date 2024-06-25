using Server.Chat.Text;
using Server.Network.Binary;

namespace Server.Network.Packet.Type.Login.Server;

public record ServerLoginDisconnectPacket(IComponent Reason) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.TEXT_COMPONENT, Reason);
    }
}