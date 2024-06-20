using Tachyon.Network.Packet.Registry;

namespace Tachyon.Network.Packet.Processor;

internal class PlayPacketProcessor(PacketRegistry packetRegistry, PlayerConnection connection) : PacketProcessor(packetRegistry, connection)
{
    public override void Process(IClientPacket packet)
    {
        
    }
}