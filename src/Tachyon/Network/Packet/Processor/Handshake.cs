using Tachyon.Network.Packet.Type.Handshake.Client;
using Tachyon.Network;

namespace Tachyon.Network.Packet.Processor;

internal class Handshake : PacketProcessor
{
    public override void Process(IPacket packet)
    {
        if (packet is ClientHandshakePacket handshake)
        {
            var next = handshake.NextState;
            if (next == ConnectionState.STATUS)
            {
                _connection.SetConnectionState(ConnectionState.STATUS);
            }
        }
    }

    internal Handshake(PlayerConnection connection) : base(connection)
    {
    }
}