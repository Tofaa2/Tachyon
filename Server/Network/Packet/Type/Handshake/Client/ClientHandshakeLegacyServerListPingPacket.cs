using Server.Network.Binary;

namespace Server.Network.Packet.Type.Handshake.Client;

public class ClientHandshakeLegacyServerListPingPacket : IClientPacket
{

    public byte Payload { get; private set; }
    
    public void Read(BinaryBuffer reader)
    {
        Payload = reader.Read(BinaryBuffer.BYTE);
    }
}