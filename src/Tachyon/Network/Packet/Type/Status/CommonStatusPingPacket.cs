using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Status;

public class CommonStatusPingPacket : IClientPacket, IServerPacket
{

    public long Payload { get; private set; }
    
    public CommonStatusPingPacket(long Payload)
    {
        this.Payload = Payload;
    }
    
    public CommonStatusPingPacket() {}
    
    
    public void Read(BinaryBuffer reader)
    {
        Payload = reader.Read(BinaryBuffer.LONG);
    }

    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.LONG,Payload);
    }
}