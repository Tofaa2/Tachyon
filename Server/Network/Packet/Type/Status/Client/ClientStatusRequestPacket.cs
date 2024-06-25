using Server.Network.Binary;

namespace Server.Network.Packet.Type.Status.Client;

public class ClientStatusRequestPacket : IClientPacket
{
    public void Read(BinaryBuffer reader)
    {
        reader.Buffer.SkipBytes(reader.Buffer.ReadableBytes);
    }
}