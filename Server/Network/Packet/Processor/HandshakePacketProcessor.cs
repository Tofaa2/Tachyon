using Server.Chat.Text;
using Server.Network.Connection;
using Server.Network.Packet.Registry;
using Server.Network.Packet.Type.Handshake.Client;

namespace Server.Network.Packet.Processor;

internal class HandshakePacketProcessor(PacketRegistry packetRegistry, PlayerConnection connection)
    : PacketProcessor(packetRegistry, connection)
{
    private static readonly TextComponent OutdatedServerMessage = new("Outdated server! I'm still on " + MinecraftConstants.VERSION_NAME + " :(", NamedTextColor.Red);

    private static readonly TextComponent OutdatedClientMessage =
        new("Outdated client! Please use " + MinecraftConstants.VERSION_NAME + " :(", NamedTextColor.Red);

    public override void Process(IClientPacket packet)
    {
        if (packet is not ClientHandshakePacket handshake) return;
        switch (handshake.ConnectionIntent)
        {
            case ClientHandshakePacket.Intent.Status:
                connection.INTERNAL_SwitchConnectionState(ConnectionState.Status);
                break;
            case ClientHandshakePacket.Intent.Login:
            {
                if (handshake.ProtocolVersion < MinecraftConstants.PROTOCOL_VERSION)
                    connection.Disconnect(OutdatedClientMessage);
                else if (handshake.ProtocolVersion > MinecraftConstants.PROTOCOL_VERSION)
                    connection.Disconnect(OutdatedServerMessage);
                else
                {
                    Tachyon.Logger.Info("Switched connection state to login.");
                    connection.INTERNAL_SwitchConnectionState(ConnectionState.Login);                    
                }
                break;
            }
            case ClientHandshakePacket.Intent.Transfer:
                Tachyon.Logger.Error("Transfer packet intents are not supported yet.");
                break;
            default:
                throw new ArgumentOutOfRangeException();
        }
    }
}