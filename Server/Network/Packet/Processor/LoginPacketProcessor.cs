using Server.Chat.Text;
using Server.Datapack;
using Server.Item.Armor;
using Server.Network.Connection;
using Server.Network.Packet.Registry;
using Server.Network.Packet.Type.Configuration.Server;
using Server.Network.Packet.Type.Login.Client;
using Server.Network.Packet.Type.Login.Server;
using Server.Util;

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
                connection.INTERNAL_SwitchConnectionState(ConnectionState.Configuration);
                connection.SendPacketNow(new ServerConfigurationKnownDataPacksPacket(new EmptyCollection<KnownDataPack>()));
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
                var uuid = Tachyon.ConnectionManager.CreatePlayerConnectionUuid(connection, packet.Username);
                Tachyon.ConnectionManager.CreatePlayer(connection, uuid, packet.Username);
            }
            catch (Exception e)
            {
                connection.Disconnect(IComponent.Text("Failed to login: " + e.Message, NamedTextColor.Red));
            }
        });
    }
    
}