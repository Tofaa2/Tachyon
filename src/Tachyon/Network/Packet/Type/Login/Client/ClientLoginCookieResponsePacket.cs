using DotNetty.Buffers;
using Tachyon.Namespace;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Client;

public class ClientLoginCookieResponsePacket : IClientPacket
{

    public NamespaceId Identifier;
    public bool HasPayload;
    public int PayloadLength;
    public byte[]? Payload;
    
    public void Read(IByteBuffer reader)
    {
        Identifier = reader.ReadNamespace();
        HasPayload = reader.ReadBoolean();
        if (HasPayload)
        {
            PayloadLength = reader.ReadVarInt();
            Payload = reader.ReadBytes(PayloadLength).Array;
        }
    }
}