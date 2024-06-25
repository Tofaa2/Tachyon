using Server.Chat.Text;
using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationDisconnectPacket(IComponent Reason) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.TEXT_COMPONENT, Reason);
    }
}