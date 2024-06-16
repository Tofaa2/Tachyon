using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Handshake.Client;

public record ClientHandshakeLegacyServerListPingPacket(byte Payload) : IPacket
{
    
    public ClientHandshakeLegacyServerListPingPacket(IByteBuffer buffer) : this(buffer.ReadByte())
    {
    }
    
    public void Write(IByteBuffer writer)
    {
        writer.WriteByte(Payload);
    }
} 