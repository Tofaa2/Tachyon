using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Status.Client;

public record ClientStatusStatusRequestPacket() : IPacket
{
    
    public ClientStatusStatusRequestPacket(IByteBuffer buffer): this() {}
    
    public void Write(IByteBuffer buffer)
    {
        
    }
    
}