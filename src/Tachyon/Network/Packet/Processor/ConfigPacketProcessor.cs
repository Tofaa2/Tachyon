namespace Tachyon.Network.Packet.Processor;

internal class ConfigPacketProcessor(Tachyon server, PlayerConnection connection) : PacketProcessor(server, connection)
{
    public override void Process(IClientPacket packet)
    {
        throw new NotImplementedException();
    }
}