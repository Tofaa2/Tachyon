using Server.Network.Packet.Type.Configuration.Client;
using Server.Network.Packet.Type.Configuration.Server;

namespace Server.Network.Packet.Registry;

internal  class ConfigurationPacketSupplier : PacketSupplier
{

    internal ConfigurationPacketSupplier()
    {
        RegisterClient(0x00, () => new ClientConfigurationClientInfoPacket());
        RegisterClient(0x01, () => new ClientConfigurationCookieResponsePacket());
        RegisterClient(0x02, () => new ClientConfigurationPluginMessagePacket());
        RegisterClient(0x03, () => new ClientConfigurationAcknowledgeFinishPacket());
        RegisterClient(0x04, () => new ClientConfigurationKeepAlivePacket());
        RegisterClient(0x05, () => new ClientConfigurationPongPacket());
        RegisterClient(0x06, () => new ClientConfigurationResourcePackResponsePacket());
        RegisterClient(0x07, () => new ClientConfigurationKnownDataPacksPacket());
        
        RegisterServer<ServerConfigurationCookieRequestPacket>(0x00);
        RegisterServer<ServerConfigurationPluginMessagePacket>(0x01);
        RegisterServer<ServerConfigurationDisconnectPacket>(0x02);
        RegisterServer<ServerConfigurationFinishPacket>(0x03);
        RegisterServer<ServerConfigurationKeepAlivePacket>(0x04);
        RegisterServer<ServerConfigurationPingPacket>(0x05);
        RegisterServer<ServerConfigurationResetChatPacket>(0x06);
        RegisterServer<ServerConfigurationRegistryPacket>(0x07);
        RegisterServer<ServerConfigurationRemoveResourcePackPacket>(0x08);
        RegisterServer<ServerConfigurationAddResourcePackPacket>(0x09);
        RegisterServer<ServerConfigurationStoreCookiePacket>(0x0A);
        RegisterServer<ServerConfigurationTransferPacket>(0x0B);
        RegisterServer<ServerConfigurationFeatureFlagsPacket>(0x0C);
        RegisterServer<ServerConfigurationUpdateTagsPacket>(0x0D);
        RegisterServer<ServerConfigurationKnownDataPacksPacket>(0x0E);
        RegisterServer<ServerConfigurationCustomReportDetailsPacket>(0x0F);
        RegisterServer<ServerConfigurationServerLinksPacket>(0x10);
    }
    
}