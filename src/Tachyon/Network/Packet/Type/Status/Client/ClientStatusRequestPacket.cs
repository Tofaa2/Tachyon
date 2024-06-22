using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Status.Client;

public class ClientStatusRequestPacket : IClientPacket
{
    public void Read(BinaryBuffer reader)
    {
        reader.Buffer.SkipBytes(reader.Buffer.ReadableBytes);
    }
}