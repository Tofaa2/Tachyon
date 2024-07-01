using Server.Network.Binary;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlayChunkBatchFinishedPacket(int Size) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.VAR_INT, Size);
    }
}