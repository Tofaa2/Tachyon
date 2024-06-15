using Tachyon.Network.Packet;

namespace Tachyon.Network.Processor;

internal class LoginPacketProcessor(TachyonServer _server, PlayerConnection _connection) : PacketProcessor(_server, _connection)
{
    public override void Handle(IClientPacket packet)
    {
        throw new NotImplementedException();
    }
}