using Server.Entity;
using Server.Item.Armor;
using Server.Network.Connection;
using Server.Network.Packet.Registry;
using Server.Network.Packet.Type.Configuration.Client;
using Server.Network.Packet.Type.Configuration.Server;
using Server.Network.Packet.Type.Play.Server;
using Server.Util.Collections;

namespace Server.Network.Packet.Processor;

internal class ConfigPacketProcessor(PacketRegistry packetRegistry, PlayerConnection connection) : PacketProcessor(packetRegistry, connection)
{
    public override void Process(IClientPacket packet)
    {
        switch (packet)
        {
            case ClientConfigurationClientInfoPacket s:
                connection.Player!.PlayerSettings = s.Settings;
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
                connection.INTERNAL_SwitchConnectionState(ConnectionState.Play);
                connection.SendPacketNow(new ServerPlayJoinGamePacket(
                    connection.Player!.EntityId,
                    true,
                    new EmptyCollection<string>(),
                    100,
                    6, 6,
                    false, true, true,
                    0, "minecraft:overworld",
                    123321L,
                    Player.GameMode.Creative,
                    null,
                    true,
                    false,
                    null,
                    1, false
                    ));
                break;
            }
        }
    }
}