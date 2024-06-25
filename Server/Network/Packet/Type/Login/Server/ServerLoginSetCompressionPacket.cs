using Server.Network.Binary;

namespace Server.Network.Packet.Type.Login.Server;

public record ServerLoginSetCompressionPacket(int Threshold) : IServerPacket 
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.VAR_INT,Threshold);
    }
}