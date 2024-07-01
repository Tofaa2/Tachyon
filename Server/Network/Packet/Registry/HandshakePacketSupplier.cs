using Server.Network.Packet.Processor;
using Server.Network.Packet.Type.Handshake.Client;

namespace Server.Network.Packet.Registry;

internal class HandshakePacketSupplier : PacketSupplier
{
     internal HandshakePacketSupplier()
     {
          // Handshake
          RegisterClient(0x00, () => new ClientHandshakePacket());
          RegisterClient(0xFE, () => new ClientHandshakeLegacyServerListPingPacket());
     }
    
}