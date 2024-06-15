using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Status.Client;

public class ClientStatusLegacyServerListPingPacket : IClientPacket
{

    public byte Payload;

    public void Read(NetworkBuffer reader)
    {
        Payload = reader.Read(NetworkBufferTypes.BYTE);
    }

    public void Write(NetworkBuffer writer)
    {
        writer.Write(NetworkBufferTypes.BYTE, Payload);
    }
}