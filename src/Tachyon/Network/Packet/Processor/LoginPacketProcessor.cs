using Tachyon.Network.Packet.Type.Login.Client;

namespace Tachyon.Network.Packet.Processor;

internal class LoginPacketProcessor(Tachyon server, PlayerConnection connection) : PacketProcessor(server, connection)
{
    public override void Process(IClientPacket packet)
    {
        switch (packet)
        {
            case ClientLoginStartPacket p:
                Console.WriteLine($"{p.Username} with id {p.Uuid}");
                break;
        }
    }


    private void HandleLoginStart(ClientLoginStartPacket packet)
    {
        connection.INTERNAL_SetUserData(packet.Username, packet.Uuid);
        // TODO: Mojang Auth

        Task.Run(() =>
        {
            Guid playerUuid;
            
        });
    }
    
}