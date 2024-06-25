using Server.Namespace;
using Server.Network.Binary;

namespace Server.Network.Packet.Type.Login.Client;

public class ClientLoginCookieResponsePacket : IClientPacket
{

    public NamespaceId Identifier;
    public bool HasPayload;
    public int PayloadLength;
    public byte[]? Payload;
    
    public void Read(BinaryBuffer reader)
    {
        Identifier = reader.Read(BinaryBuffer.NAMESPACE_ID);
        HasPayload = reader.Read(BinaryBuffer.BOOL);
        if (HasPayload)
        {
            PayloadLength = reader.Read(BinaryBuffer.VAR_INT);
            Payload = reader.ReadBytes(PayloadLength);
        }
    }
}