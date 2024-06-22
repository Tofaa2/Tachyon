using DotNetty.Buffers;
using Tachyon.Namespace;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Server;

public record ServerLoginCookieRequestPacket(NamespaceId Key) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.NAMESPACE_ID, Key);
    }
}