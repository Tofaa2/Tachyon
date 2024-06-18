using Tachyon.Network.Packet.Type.Login.Client;
using Tachyon.Network.Packet.Type.Login.Server;
using Tachyon.Text;

namespace Tachyon.Network.Packet.Processor;

internal class LoginPacketProcessor(Tachyon server, PlayerConnection connection) : PacketProcessor(server, connection)
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
                Console.WriteLine("Login ackgnowledged!!!!!!");
                break;
        }
    }


    private void HandleLoginStart(ClientLoginStartPacket packet)
    {
        connection.INTERNAL_SetUserData(packet.Username, packet.Uuid);
        // TODO: Mojang Auth

        connection.SendPacketNow(new ServerLoginSuccessPacket(Guid.NewGuid(), "tofaa", 0, null, false));
        Task.Run(() =>
        {
            try
            {
                var uuid = server.ConnectionManager.CreatePlayerConnectionUuid(connection, packet.Username);
                server.ConnectionManager.CreatePlayer(connection, uuid, packet.Username);
            }
            catch (Exception e)
            {
                connection.Disconnect(IComponent.Text("Failed to login: " + e.Message, NamedTextColor.Red));
            }
        });
    }
    
}