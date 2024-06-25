using System.Collections.Concurrent;
using DotNetty.Transport.Channels;
using Server.Entity;
using Server.Network.Packet.Type.Login.Server;
using Server.Util;

namespace Server.Network.Connection;

public class ConnectionManager
{
    
    public readonly ConcurrentDictionary<IChannel, PlayerConnection> Connections = new();
    public readonly ConcurrentDictionary<Guid, Player> Players = new();
    public readonly ConcurrentDictionary<PlayerConnection, Player> PlayersByConnection = new();

    private IUniqueIdProvider _uuidProvider = IUniqueIdProvider.Offline;
    private IPlayerProvider _playerProvider = IPlayerProvider.Default;
    
    public ConnectionManager()
    {
        Check.PostInit("ConnectionManager Constructor");
    }


    public Player CreatePlayer(PlayerConnection connection, Guid uuid, string username)
    {
        var player = _playerProvider.Provide(connection, username, uuid);
        Players.TryAdd(uuid, player);
        PlayersByConnection.TryAdd(connection, player);

        Task task = TransitionLoginToConfig(player);

        return player;
    }

    public Task TransitionLoginToConfig(Player player)
    {
        return Task.Run(() =>
        {
            var connection = player.Connection;
            
            ServerLoginSuccessPacket p = new(player.Uuid, player.Username, 0, false);
            connection.SendPacketNow(p);
        });
    }

    public Guid CreatePlayerConnectionUuid(PlayerConnection connection, string username)
    {
        return _uuidProvider.Provide(username, connection);
    }

    public PlayerConnection? GetPlayerFromUsername(string username)
    {
        return Connections.Values.FirstOrDefault(conn => conn.Username == username);
    }
    
}