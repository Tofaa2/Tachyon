using Server.Network.Binary;
using Server.World;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlayChangeDifficultyPacket(Difficulty Difficulty, bool Locked) : IServerPacket
{
    
    
    
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.VAR_INT, (int) Difficulty);
        writer.Write(BinaryBuffer.BOOL, Locked);
    }
}