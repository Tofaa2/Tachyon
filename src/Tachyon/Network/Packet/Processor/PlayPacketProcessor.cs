namespace Tachyon.Network.Packet.Processor;

internal class PlayPacketProcessor(Tachyon server, PlayerConnection connection) : PacketProcessor(server, connection)
{
    public override void Process(IClientPacket packet)
    {
        
    }
}