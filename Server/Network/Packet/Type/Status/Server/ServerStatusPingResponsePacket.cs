using Server.Network.Binary;

namespace Server.Network.Packet.Type.Status.Server;

public record ServerStatusPingResponsePacket(long Payload) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.LONG,Payload);
    }
}