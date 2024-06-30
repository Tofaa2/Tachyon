using Server.Network.Binary;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlayEntityAnimationPacket(int EntityId, Entity.Entity.Animation Animation) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.VAR_INT, EntityId);
        writer.Buffer.WriteByte((byte)Animation);
    }
}