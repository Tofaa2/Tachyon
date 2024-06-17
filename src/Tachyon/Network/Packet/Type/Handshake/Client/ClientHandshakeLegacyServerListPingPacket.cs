using DotNetty.Buffers;

namespace Tachyon.Network.Packet.Type.Handshake.Client;

public class ClientHandshakeLegacyServerListPingPacket : IClientPacket
{

    public byte Payload { get; private set; }
    
    public void Read(IByteBuffer reader)
    {
        Payload = reader.ReadByte();
    }
}