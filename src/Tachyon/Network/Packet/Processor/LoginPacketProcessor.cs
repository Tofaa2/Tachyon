using Tachyon.Chat.Text;
using Tachyon.Network.Connection;
using Tachyon.Network.Packet.Registry;
using Tachyon.Network.Packet.Type.Configuration.Server;
using Tachyon.Network.Packet.Type.Login.Client;
using Tachyon.Network.Packet.Type.Login.Server;

namespace Tachyon.Network.Packet.Processor;

internal class LoginPacketProcessor(PacketRegistry packetRegistry, PlayerConnection connection)
    : PacketProcessor(packetRegistry, connection)
{
    public override void Process(IClientPacket packet)
    {
        switch (packet)
        {
            case ClientLoginStartPacket p:
                Console.WriteLine($"{p.Username} with id {p.Uuid}");
                HandleLoginStart(p);
                break;
            case ClientLoginPluginResposePacket p:
                break;
            case ClientLoginAckgnowledgedPacket p:
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