using Server.Network.Binary;
using Server.Position;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlayBlockActionPacket(ICoordinate Location, byte ActionId, byte ActionParam, int BlockType) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.BLOCK_POSITION, Location);
        writer.Buffer.WriteByte(ActionId);
        writer.Buffer.WriteByte(ActionParam);
        writer.Write(BinaryBuffer.VAR_INT, BlockType);
    }
}