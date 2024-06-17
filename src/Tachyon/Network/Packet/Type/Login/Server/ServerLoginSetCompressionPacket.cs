using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Server;

public record ServerLoginSetCompressionPacket(int Threshold) : IServerPacket 
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteVarInt(Threshold);
    }
}