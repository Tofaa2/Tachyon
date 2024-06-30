using Server.Network.Binary;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlaySpawnExperienceOrbPacket(int EntityId, double X, double Y, double Z, short Count) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.VAR_INT, EntityId);
        writer.Write(BinaryBuffer.DOUBLE, X);
        writer.Write(BinaryBuffer.DOUBLE, Y);
        writer.Write(BinaryBuffer.DOUBLE, Z);
        writer.Write(BinaryBuffer.SHORT, Count);
    }
}