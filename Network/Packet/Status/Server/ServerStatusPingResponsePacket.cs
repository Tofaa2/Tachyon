using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Status.Server;

public class ServerStatusPingResponsePacket : IServerPacket
{
    public long Payload;
    
    public void Read(NetworkBuffer reader)
    {
        Payload = reader.Read(NetworkBufferTypes.LONG);
    }

    public void Write(NetworkBuffer writer)
    {
        writer.Write(NetworkBufferTypes.LONG, Payload);
    }

    public int Id => ServerPacketIdentifier.STATUS_PING_RESPONSE;

}