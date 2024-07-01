using Server.Item.Armor;
using Server.Network.Connection;
using Server.Network.Packet.Registry;
using Server.Network.Packet.Type.Configuration.Client;
using Server.Network.Packet.Type.Configuration.Server;
using Server.Network.Packet.Type.Play.Server;

namespace Server.Network.Packet.Processor;

internal class ConfigPacketProcessor(PacketRegistry packetRegistry, PlayerConnection connection) : PacketProcessor(packetRegistry, connection)
{
    public override void Process(IClientPacket packet)
    {
        switch (packet)
        {
            case ClientConfigurationClientInfoPacket s:
                connection.SendPacketNow(ITrimMaterial.CreatePacket());
                connection.SendPacketNow(new ServerConfigurationFinishPacket());
                Tachyon.LOGGER.Info("Configuration step finished");
                connection.INTERNAL_SwitchConnectionState(ConnectionState.Play);
                break;
            // case ClientConfigurationKnownDataPacksPacket d:
            //     string packs = "";
            //     foreach (var pack in d.KnownDataPacks)
            //     {
            //         packs += pack.Namespace;
            //     }
            //     Tachyon.LOGGER.Info("Known data packs: "  + packs);
            //     connection.SendPacketNow(ITrimMaterial.CreatePacket());
            //     connection.SendPacketNow(new ServerConfigurationFinishPacket());
            //     break;
            case ClientConfigurationAcknowledgeFinishPacket p:
            {
                Tachyon.LOGGER.Info("Configuration step finished");
                connection.INTERNAL_SwitchConnectionState(ConnectionState.Play);
                break;
            }
        }
    }
}