using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Status.Client;

public class ClientStatusRequestPacket : IClientPacket
{
    public void Read(IByteBuffer reader)
    {
        // Nothing to read;
    }
}