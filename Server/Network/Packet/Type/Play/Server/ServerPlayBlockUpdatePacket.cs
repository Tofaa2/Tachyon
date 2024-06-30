using Server.Network.Binary;
using Server.Position;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlayBlockUpdatePacket(ICoordinate Location, int BlockId) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.BLOCK_POSITION, Location);
        writer.Write(BinaryBuffer.VAR_INT, BlockId);
    }
}