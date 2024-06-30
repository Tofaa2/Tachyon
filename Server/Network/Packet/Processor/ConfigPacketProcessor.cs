using Server.Network.Connection;
using Server.Network.Packet.Registry;
using Server.Network.Packet.Type.Configuration.Client;

namespace Server.Network.Packet.Processor;

internal class ConfigPacketProcessor(PacketRegistry packetRegistry, PlayerConnection connection) : PacketProcessor(packetRegistry, connection)
{
    public override void Process(IClientPacket packet)
    {
        switch (packet)
        {
            case ClientConfigurationAcknowledgeFinishPacket p:
            {
                Tachyon.LOGGER.Info("Configuration step finished");
                connection.INTERNAL_SwitchConnectionState(ConnectionState.Play);
                break;
            }
        }
    }
}