using Server.Network.Binary;
using Server.Position;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlayBlockEntityDataPacket(ICoordinate Location, int Type, Nbt.Nbt Data) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.BLOCK_POSITION, Location);
        writer.Write(BinaryBuffer.VAR_INT, Type);
        writer.Write(BinaryBuffer.NBT, Data);
    }
}