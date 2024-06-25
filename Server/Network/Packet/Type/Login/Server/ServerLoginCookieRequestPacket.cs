using Server.Namespace;
using Server.Network.Binary;

namespace Server.Network.Packet.Type.Login.Server;

public record ServerLoginCookieRequestPacket(NamespaceId Key) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.NAMESPACE_ID, Key);
    }
}