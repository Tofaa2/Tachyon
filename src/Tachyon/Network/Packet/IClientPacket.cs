using DotNetty.Buffers;

namespace Tachyon.Network.Packet;

public interface IClientPacket : IPacket
{
    
    void Read(IByteBuffer reader);
    
}