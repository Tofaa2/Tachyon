using Tachyon.Network.Connection;
using Tachyon.Network.Packet.Type.Handshake.Client;
using Tachyon.Text;

namespace Tachyon.Network.Packet.Processor;

internal class HandshakePacketProcessor(Tachyon server, PlayerConnection connection)
    : PacketProcessor(server, connection)
{
    private static TextComponent OutdatedServerMessage =
        new("Outdated server! I'm still on " + Tachyon.Version + " :(", NamedTextColor.Red);

    private static TextComponent OutdatedClientMessage =
        new("Outdated client! Please use " + Tachyon.Version + " :(", NamedTextColor.Red);

    public override void Process(IClientPacket packet)
    {
        if (packet is not ClientHandshakePacket handshake) return;
        switch (handshake.ConnectionIntent)
        {
            case ClientHandshakePacket.Intent.Status:
                connection.INTERNAL_SwitchConnectionState(ConnectionState.Status);
                Console.WriteLine("Switched connection state to status.");
                break;
            case ClientHandshakePacket.Intent.Login:
            {
                connection.INTERNAL_SwitchConnectionState(ConnectionState.Login);
                if (handshake.ProtocolVersion < Tachyon.ProtocolVersion)
                    connection.Disconnect(OutdatedClientMessage);
                else if (handshake.ProtocolVersion > Tachyon.ProtocolVersion)
                    connection.Disconnect(OutdatedServerMessage);
                else
                    Console.WriteLine("Switched connection state to login.");
                break;
            }
            case ClientHandshakePacket.Intent.Transfer:
                Console.WriteLine("Transfer packet intents are not supported yet.");
                break;
            default:
                throw new ArgumentOutOfRangeException();
        }
    }
}