using System.Collections.Concurrent;
using Tachyon.Network.Connection;
using Tachyon.Network.Packet.Type.Configuration.Client;
using Tachyon.Network.Packet.Type.Handshake.Client;
using Tachyon.Network.Packet.Type.Login.Client;
using Tachyon.Network.Packet.Type.Login.Server;
using Tachyon.Network.Packet.Type.Status;
using Tachyon.Network.Packet.Type.Status.Client;
using Tachyon.Network.Packet.Type.Status.Server;
using Tachyon.Util;

namespace Tachyon.Network.Packet.Registry;

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
        RegisterClient(ConnectionState.Login, 0x03, () => new ClientLoginAckgnowledgedPacket());
        RegisterClient(ConnectionState.Login, 0x04, () => new ClientLoginCookieResponsePacket());
        
        // Configuration
        RegisterClient(ConnectionState.Configuration, 0x00, () => new ClientConfigurationClientInfoPacket());
        RegisterClient(ConnectionState.Configuration, 0x03, () => new ClientAcknowledgeFinishConfiguration());
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