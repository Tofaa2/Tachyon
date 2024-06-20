using Tachyon.Network.Packet.Registry;
using Tachyon.Network.Packet.Type.Status;
using Tachyon.Network.Packet.Type.Status.Client;
using Tachyon.Network.Packet.Type.Status.Server;
using Tachyon.Ping;

namespace Tachyon.Network.Packet.Processor;

internal class StatusPacketProcessor(PacketRegistry packetRegistry, PlayerConnection connection) : PacketProcessor(packetRegistry, connection)
{
    public override void Process(IClientPacket packet)
    {
        switch (packet)
        {
            case ClientStatusRequestPacket p:

                ServerListPingResponse r = new();
                r.AddSamplePlayer(Guid.NewGuid(), "Tofaa");
                connection.SendPacketNow(new ServerStatusResponsePacket(r));
                break;
            case CommonStatusPingPacket p1:
                connection.SendPacketNow(new CommonStatusPingPacket(p1.Payload));
                connection.Disconnect();
                break;
        }
    }
}