using Tachyon.Network.Packet.Status.Client;

namespace Tachyon.Network.Registry;

internal class StatusPacketRegistry : ClientPacketRegistry
{

    internal StatusPacketRegistry()
    {
        Register(NextId(), () => new ClientStatusRequestPacket());
        Register(NextId(), () => new ClientStatusLegacyServerListPingPacket());
    }
    
}