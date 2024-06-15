
using System.Collections.Concurrent;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;
using Tachyon.Network.Packet;
using Tachyon.Network.Registry;

namespace Tachyon.Network;

public class ConnectionManager(TachyonServer server)
{

    private readonly ConcurrentDictionary<IChannelHandlerContext, PlayerConnection> _connectionToPlayerConnection =
        new();
    
    public PlayerConnection CreateConnection(IChannelHandlerContext context)
    {
        var conn = new PlayerConnection(server, (ISocketChannel) context.Channel);
        _connectionToPlayerConnection[context] = conn;
        conn.SwitchConnectionState(ConnectionState.HANDSHAKE);
        return conn;
    }

    public PlayerConnection? RemoveConnection(IChannelHandlerContext context)
    {
        return _connectionToPlayerConnection.TryRemove(context, out var conn) ? conn : null;
    }
    
    public PlayerConnection? GetConnection(IChannelHandlerContext context)
    {
        return _connectionToPlayerConnection.TryGetValue(context, out var conn) ? conn : null;
    }
    
}