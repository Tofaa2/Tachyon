using DotNetty.Buffers;

namespace Tachyon.Network.Packet;

public interface IServerPacket : IPacket
{
    
    public int Id { get; }
}