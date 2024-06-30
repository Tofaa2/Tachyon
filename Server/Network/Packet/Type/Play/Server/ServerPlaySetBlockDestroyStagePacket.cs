using Server.Network.Binary;
using Server.Position;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlaySetBlockDestroyStagePacket(int EntityId, ICoordinate Location, byte Stage) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.VAR_INT, EntityId);
        writer.Write(BinaryBuffer.BLOCK_POSITION, Location);
        writer.Write(BinaryBuffer.BYTE, Stage);
    }
}