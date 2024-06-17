using DotNetty.Buffers;

namespace Tachyon.Network.Packet;

public interface IServerPacket : IPacket
{
    
    void Write(IByteBuffer writer);
    
}