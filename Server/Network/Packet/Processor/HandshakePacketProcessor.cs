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
                global::Server.Tachyon.LOGGER.Info("Switched connection state to status.");
                break;
            case ClientHandshakePacket.Intent.Login:
            {
                connection.INTERNAL_SwitchConnectionState(ConnectionState.Login);
                if (handshake.ProtocolVersion < MinecraftConstants.PROTOCOL_VERSION)
                    connection.Disconnect(OutdatedClientMessage);
                else if (handshake.ProtocolVersion > MinecraftConstants.PROTOCOL_VERSION)
                    connection.Disconnect(OutdatedServerMessage);
                else
                    Tachyon.LOGGER.Info("Switched connection state to login.");
                break;
            }
            case ClientHandshakePacket.Intent.Transfer:
                global::Server.Tachyon.LOGGER.Error("Transfer packet intents are not supported yet.");
                break;
            default:
                throw new ArgumentOutOfRangeException();
        }
    }
}