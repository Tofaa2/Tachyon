using Server.Network.Binary;
using Server.Position;
using static Server.Network.Binary.BinaryBuffer;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlaySpawnEntityPacket(
    int EntityId,
    Guid Uuid,
    int Type,
    Location Location,
    float HeadRotation,
    int Data,
    short VelocityX,
    short VelocityY,
    short VelocityZ
    ) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        
        writer.Write(VAR_INT, EntityId);
        writer.Write(UUID, Uuid);
        writer.Write(VAR_INT, Type);
        
        writer.Write(DOUBLE, Location.X);
        writer.Write(DOUBLE, Location.Y);
        writer.Write(DOUBLE, Location.Z);
        
        writer.Write(BYTE, (byte) (Location.Pitch * 256 / 360));
        writer.Write(BYTE, (byte) (Location.Yaw * 256 / 360));
        writer.Write(BYTE, (byte) (HeadRotation * 256 / 360));

        writer.Write(VAR_INT, Data);
        
        writer.Write(SHORT, VelocityX);
        writer.Write(SHORT, VelocityY);
        writer.Write(SHORT, VelocityZ);
    }
}