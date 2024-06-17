namespace Tachyon.Network.Packet.Processor;

internal class LoginPacketProcessor(Tachyon server, PlayerConnection connection) : PacketProcessor(server, connection)
{
    public override void Process(IClientPacket packet)
    {
        
    }
}