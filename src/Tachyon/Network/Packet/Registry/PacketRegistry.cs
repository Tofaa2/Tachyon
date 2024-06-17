using System.Collections.Concurrent;
using DotNetty.Buffers;
using Tachyon.Network.Connection;
using Tachyon.Network.Packet.Type.Handshake.Client;
using Tachyon.Network.Packet.Type.Status;
using Tachyon.Network.Packet.Type.Status.Client;
using Tachyon.Network.Packet.Type.Status.Server;

namespace Tachyon.Network.Packet.Registry;

public class PacketRegistry
{
    private ConcurrentDictionary<System.Type, int> _serverPacketTypes = new();

    private ConcurrentDictionary<int, Func<IClientPacket>>[] _clientPackets =
        new ConcurrentDictionary<int, Func<IClientPacket>>[5];

    public PacketRegistry(Tachyon server)
    {
        for (int i = 0; i < _clientPackets.Length; i++)
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