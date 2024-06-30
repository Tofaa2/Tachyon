using Server.Namespace;
using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationStoreCookiePacket(NamespaceId Key, byte[] Payload) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        if (Payload.Length > 5120)
        {
            throw new ArgumentOutOfRangeException(nameof(Payload), "Payload too large");
        }
        writer.Write(BinaryBuffer.NAMESPACE_ID, Key);
        writer.Write(BinaryBuffer.BYTE_ARRAY, Payload);
    }
}