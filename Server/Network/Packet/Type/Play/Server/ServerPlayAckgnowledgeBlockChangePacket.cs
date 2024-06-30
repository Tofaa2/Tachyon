using Server.Network.Binary;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlayAckgnowledgeBlockChangePacket(int SequenceId) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.VAR_INT, SequenceId);
    }
}