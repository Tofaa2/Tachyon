using Tachyon.Network.Connection;
using Tachyon.Network.Packet.Type.Handshake.Client;

namespace Tachyon.Network.Packet.Processor;

internal class HandshakePacketProcessor(Tachyon server, PlayerConnection connection) : PacketProcessor(server, connection)
{
    public override void Process(IClientPacket packet)
    {
        if (packet is ClientHandshakePacket handshake)
        {

            switch (handshake.ConnectionIntent)
            {
                case ClientHandshakePacket.Intent.Status:
                    connection.INTERNAL_SwitchConnectionState(ConnectionState.Status);
                    Console.WriteLine("Switched connection state to status.");
                    break;
                case ClientHandshakePacket.Intent.Login:
                    Console.WriteLine("TODO::::");
                    break;
                case ClientHandshakePacket.Intent.Transfer:
                    Console.WriteLine("Transfer packet intents are not supported yet.");
                    break;
            }
        }
    }
}