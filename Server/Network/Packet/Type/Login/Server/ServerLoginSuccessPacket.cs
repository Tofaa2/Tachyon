using Server.Network.Binary;

namespace Server.Network.Packet.Type.Login.Server;

public record ServerLoginSuccessPacket(
    Guid Uuid,
    string Username,
    int Properties,
    bool StrictErrorHandling
) : IServerPacket
{
    
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.UUID, Uuid);
        writer.Write(BinaryBuffer.STRING, Username);
        writer.Write(BinaryBuffer.VAR_INT, Properties);
        writer.Write(BinaryBuffer.BOOL, StrictErrorHandling);
    }
}