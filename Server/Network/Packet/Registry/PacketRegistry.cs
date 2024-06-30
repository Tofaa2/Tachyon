using System.Collections.Concurrent;
using Server.Network.Connection;
using Server.Network.Packet.Type.Configuration.Client;
using Server.Network.Packet.Type.Configuration.Server;
using Server.Network.Packet.Type.Handshake.Client;
using Server.Network.Packet.Type.Login.Client;
using Server.Network.Packet.Type.Login.Server;
using Server.Network.Packet.Type.Status;
using Server.Network.Packet.Type.Status.Client;
using Server.Network.Packet.Type.Status.Server;
using Server.Util;

namespace Server.Network.Packet.Registry;

public class PacketRegistry
{
    private ConcurrentDictionary<System.Type, int> _serverPacketTypes = new();

    private ConcurrentDictionary<int, Func<IClientPacket>>[] _clientPackets =
        new ConcurrentDictionary<int, Func<IClientPacket>>[5];

    public PacketRegistry()
    {
        Check.PostInit("Packet Registry constructor");
        for (var i = 0; i < _clientPackets.Length; i++)
        {
            _clientPackets[i] = new ConcurrentDictionary<int, Func<IClientPacket>>();
        }
        
        // Handshake
        RegisterClient(ConnectionState.Handshake, 0x00, () => new ClientHandshakePacket());
        RegisterClient(ConnectionState.Handshake, 0xFE, () => new ClientHandshakeLegacyServerListPingPacket());
        
        // Status
        RegisterServer<ServerStatusResponsePacket>(0x00);
        RegisterServer<CommonStatusPingPacket>(0x01);
        
        RegisterClient(ConnectionState.Status, 0x00, () => new ClientStatusRequestPacket());
        RegisterClient(ConnectionState.Status, 0x01,  () => new CommonStatusPingPacket());
        
        // Login
        RegisterServer<ServerLoginDisconnectPacket>(0x00);
        RegisterServer<ServerLoginEncryptionRequest>(0x01);
        RegisterServer<ServerLoginSuccessPacket>(0x02);
        RegisterServer<ServerLoginSetCompressionPacket>(0x03);
        RegisterServer<ServerLoginPluginRequestPacket>(0x04);
        RegisterServer<ServerLoginCookieRequestPacket>(0x05);
        
        RegisterClient(ConnectionState.Login, 0x00, () => new ClientLoginStartPacket());
        RegisterClient(ConnectionState.Login, 0x01, () => new ClientLoginEncryptionResponsePacket());
        RegisterClient(ConnectionState.Login, 0x02, () => new ClientLoginPluginResposePacket());
        RegisterClient(ConnectionState.Login, 0x03, () => new ClientLoginAcknowledgedPacket());
        RegisterClient(ConnectionState.Login, 0x04, () => new ClientLoginCookieResponsePacket());
        
        // Configuration
        RegisterClient(ConnectionState.Configuration, 0x00, () => new ClientConfigurationClientInfoPacket());
        RegisterClient(ConnectionState.Configuration, 0x01, () => new ClientConfigurationCookieResponsePacket());
        RegisterClient(ConnectionState.Configuration, 0x02, () => new ClientConfigurationPluginMessagePacket());
        RegisterClient(ConnectionState.Configuration, 0x03, () => new ClientConfigurationAcknowledgeFinishPacket());
        RegisterClient(ConnectionState.Configuration, 0x04, () => new ClientConfigurationKeepAlivePacket());
        RegisterClient(ConnectionState.Configuration, 0x05, () => new ClientConfigurationPongPacket());
        RegisterClient(ConnectionState.Configuration, 0x06, () => new ClientConfigurationResourcePackResponsePacket());
        RegisterClient(ConnectionState.Configuration, 0x07, () => new ClientConfigurationKnownDataPacksPacket());
        
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
    
    private void RegisterClient(ConnectionState state, int packetId, Func<IClientPacket> supplier)
    {
        var dict = _clientPackets[(int)state]!;
        dict[packetId] = supplier;
    }
    
    private void RegisterServer<T>(int id) where T : IPacket
    {
        _serverPacketTypes[typeof(T)] = id;
    }

    public int GetServerPacketId<T>() where T : IPacket
    {
        return _serverPacketTypes[typeof(T)];
    }
    
    public int? GetServerPacketId(System.Type type)
    {
        return _serverPacketTypes[type];
    }

    public IClientPacket? CreateClientPacket(ConnectionState state, int packetId)
    {
        var dict = _clientPackets[(int)state]!;
        return !dict.TryGetValue(packetId, out var supplier) ? null : supplier();
    }

}