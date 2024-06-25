using Server.Network.Connection;
using Server.Network.Packet.Registry;

namespace Server.Network.Packet.Processor;

internal class PlayPacketProcessor(PacketRegistry packetRegistry, PlayerConnection connection) : PacketProcessor(packetRegistry, connection)
{
    public override void Process(IClientPacket packet)
    {
        
    }
}