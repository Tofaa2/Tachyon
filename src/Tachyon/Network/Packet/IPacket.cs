using DotNetty.Buffers;

namespace Tachyon.Network.Packet;

public interface IPacket
{
    public string ToString()
    {
        return this.GetType().Name;
    }
    
}