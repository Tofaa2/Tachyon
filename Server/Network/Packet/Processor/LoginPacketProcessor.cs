using Server.Chat.Text;
using Server.Network.Connection;
using Server.Network.Packet.Registry;
using Server.Network.Packet.Type.Configuration.Server;
using Server.Network.Packet.Type.Login.Client;

namespace Server.Network.Packet.Processor;

internal class LoginPacketProcessor(PacketRegistry packetRegistry, PlayerConnection connection)
    : PacketProcessor(packetRegistry, connection)
{
    public override void Process(IClientPacket packet)
    {
        switch (packet)
        {
            case ClientLoginStartPacket p:
                Tachyon.LOGGER.Info($"{p.Username} with uuid {p.Uuid}");
                HandleLoginStart(p);
                break;
            case ClientLoginPluginResposePacket p:
                break;
            case ClientLoginAcknowledgedPacket p:
                break;
        }
    }


    private void HandleLoginStart(ClientLoginStartPacket packet)
    {
        connection.INTERNAL_SetUserData(packet.Username, packet.Uuid);
        // TODO: Mojang Auth

        Task.Run(() =>
        {
            try
            {
                var uuid = global::Server.Tachyon.ConnectionManager.CreatePlayerConnectionUuid(connection, packet.Username);
                global::Server.Tachyon.ConnectionManager.CreatePlayer(connection, uuid, packet.Username);
                connection.SendPacketNow(new ServerConfigurationFinishPacket());
                connection.INTERNAL_SwitchConnectionState(ConnectionState.Configuration);
            }
            catch (Exception e)
            {
                connection.Disconnect(IComponent.Text("Failed to login: " + e.Message, NamedTextColor.Red));
            }
        });
    }
    
}