using Server.Network.Packet.Type.Status;
using Server.Network.Packet.Type.Status.Client;
using Server.Network.Packet.Type.Status.Server;

namespace Server.Network.Packet.Registry;

internal class StatusPacketSupplier : PacketSupplier
{

    public StatusPacketSupplier()
    {
        RegisterServer<ServerStatusResponsePacket>(0x00);
        RegisterServer<CommonStatusPingPacket>(0x01);
        
        RegisterClient(0x00, () => new ClientStatusRequestPacket());
        RegisterClient(0x01,  () => new CommonStatusPingPacket());
    }
    
}