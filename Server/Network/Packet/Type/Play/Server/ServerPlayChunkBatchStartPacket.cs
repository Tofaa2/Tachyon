using Server.Network.Binary;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlayChunkBatchStartPacket : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        
    }
}