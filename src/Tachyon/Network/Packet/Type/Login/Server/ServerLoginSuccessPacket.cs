using System.Reflection.Metadata;
using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Server;

public record ServerLoginSuccessPacket(
    Guid Uuid,
    string Username,
    int Properties,
    bool StrictErrorHandling
) : IServerPacket
{
    
    public void Write(IByteBuffer writer)
    {
        writer.WriteUUID(Uuid);
        writer.WriteStr(Username);
        writer.WriteVarInt(Properties);
        writer.WriteBoolean(StrictErrorHandling);
    }
}