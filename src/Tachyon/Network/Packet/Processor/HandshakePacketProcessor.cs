using Tachyon.Network.Connection;
using Tachyon.Network.Packet.Type.Handshake.Client;
using Tachyon.Text;

namespace Tachyon.Network.Packet.Processor;

internal class HandshakePacketProcessor(Tachyon server, PlayerConnection connection) : PacketProcessor(server, connection)
{
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
                if (handshake.ProtocolVersion != 123)
                {
                    connection.Disconnect(
                        new TextComponent("Outdated server! I'm still on " + Tachyon.Version, NamedTextColor.Red)
                            .WithDecoration(TextDecoration.Italic)
                        );
                }
                break;
            }
            case ClientHandshakePacket.Intent.Transfer:
                Console.WriteLine("Transfer packet intents are not supported yet.");
                break;
        }
    }
    
}