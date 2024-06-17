using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Status;

public class CommonStatusPingPacket : IClientPacket, IServerPacket
{

    public long Payload { get; private set; }
    
    public CommonStatusPingPacket(long Payload)
    {
        this.Payload = Payload;
    }
    
    public CommonStatusPingPacket() {}
    
    
    public void Read(IByteBuffer reader)
    {
        Payload = reader.ReadLong();
    }

    public void Write(IByteBuffer writer)
    {
        writer.WriteLong(Payload);
    }
}