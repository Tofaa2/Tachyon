using Tachyon.Network.Connection;
using Tachyon.Network.Packet.Registry;
using Tachyon.Network.Packet.Type.Configuration.Client;

namespace Tachyon.Network.Packet.Processor;

internal class ConfigPacketProcessor(PacketRegistry packetRegistry, PlayerConnection connection) : PacketProcessor(packetRegistry, connection)
{
    public override void Process(IClientPacket packet)
    {
        switch (packet)
        {
            case ClientAcknowledgeFinishConfiguration p:
            {
                Console.WriteLine("Configuration step finished");
                connection.INTERNAL_SwitchConnectionState(ConnectionState.Play);
                break;
            }
        }
    }
}