using Tachyon.Network.Packet;
using Tachyon.Network.Packet.Handshake.Client;

namespace Tachyon.Network.Processor;

internal class HandshakePacketProcessor(TachyonServer _server, PlayerConnection _connection) : PacketProcessor(_server, _connection)
{
    public override void Handle(IClientPacket packet)
    {
        if (packet is not ClientHandshakePacket h) return;
        string address = h.ServerAddress;
        switch (h.NextState)
        {
            case 3: throw new Exception("Transfer intents arent supported yet");
            case 2: throw new Exception("TODO!!!!!");
            case 1:
                _connection.SwitchConnectionState(ConnectionState.STATUS);
                break;
            default: throw new Exception("Invalid next state");
        }
    }
}