using System.Collections.Concurrent;
using DotNetty.Buffers;
using Tachyon.Network.Packet.Type.Handshake.Client;
using Tachyon.Network.Packet.Type.Status.Client;
using Tachyon.Network.Packet.Type.Status.Common;
using Tachyon.Network.Packet.Type.Status.Server;

namespace Tachyon.Network.Packet;

public static class PacketRegistry
{

    private static ConcurrentDictionary<System.Type, int> _serverPacketTypes = new();

    private static ConcurrentDictionary<int, Func<IByteBuffer, IPacket>>[] _clientPackets =
        new ConcurrentDictionary<int, Func<IByteBuffer, IPacket>>[5];



    public static void Init()
    {
        
        // Handshake
        RegisterClient<ClientHandshakePacket>(ConnectionState.HANDSHAKE.Id, 0x00, buffer => new ClientHandshakePacket(buffer));
        RegisterClient<ClientHandshakeLegacyServerListPingPacket>(ConnectionState.HANDSHAKE.Id, 0xFE, buffer => new ClientHandshakeLegacyServerListPingPacket(buffer));
        
        // Status
        RegisterServer<ServerStatusStatusResponsePacket>(0x00);
        RegisterServer<CommonStatusPingPacket>(0x01);
        
        RegisterClient<ClientStatusStatusRequestPacket>(ConnectionState.STATUS.Id, 0x00, buffer => new ClientStatusStatusRequestPacket(buffer));
        RegisterClient<CommonStatusPingPacket>(ConnectionState.STATUS.Id, 0x01, buffer => new CommonStatusPingPacket(buffer));
    }
    
    
    private static void RegisterServer<T>(int id) where T : IPacket
    {
        _serverPacketTypes[typeof(T)] = id;
    }

    private static void RegisterClient<T>(int state, int packetId, Func<IByteBuffer, IPacket> supplier)
        where T : IPacket
    {
        var dict = _clientPackets[state];
        dict[packetId] = supplier;
    }
    
    private static void RegisterClient<T>(ConnectionState state, int packetId, Func<IByteBuffer, IPacket> supplier) where T : IPacket
    {
        var dict = _clientPackets[state.Id]!;
        dict[packetId] = supplier;
    }

    public static int GetServerPacketId<T>() where T : IPacket
    {
        return _serverPacketTypes[typeof(T)];
    }
    
    public static int GetServerPacketId(System.Type type)
    {
        if (!_serverPacketTypes.ContainsKey(type))
        {
            throw new ArgumentException("Packet type not registered");
        }
        return _serverPacketTypes[type];
    }

    public static IPacket? CreateClientPacket(ConnectionState state, int packetId, IByteBuffer buffer)
    {
        var dict = _clientPackets[state.Id]!;
        return dict[packetId](buffer);
    }
    
}