using Server.Namespace;
using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Client;

public class ClientConfigurationCookieResponsePacket : IClientPacket
{

    public NamespaceId Identifier { get; private set; }
    public byte[]? Data { get; private set; }

    public void Read(BinaryBuffer buffer)
    {
        Identifier = buffer.Read(BinaryBuffer.NAMESPACE_ID);
        int? length = buffer.ReadOptional(BinaryBuffer.VAR_INT);
        if (length != null)
        {
           Data = buffer.ReadBytes(length.Value);
        }
    }
}