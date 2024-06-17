using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Server;

public record ServerLoginDisconnectPacket(string JsonReason) : IServerPacket
{
    
    public void Write(IByteBuffer writer)
    {
        writer.WriteStr(JsonReason);
    }
}