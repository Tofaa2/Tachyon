using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Status.Common;

public record CommonStatusPingPacket(long Payload) : IPacket 
{
    
    public CommonStatusPingPacket(IByteBuffer buffer): this(buffer.ReadLong()) {}
    
    public void Write(IByteBuffer writer)
    {
        writer.WriteLong(Payload);
    }
}