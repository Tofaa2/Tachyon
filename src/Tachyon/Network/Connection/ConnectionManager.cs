using System.Collections.Concurrent;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;

namespace Tachyon.Network.Connection;

public class ConnectionManager
{
    
    public readonly ConcurrentDictionary<IChannel, PlayerConnection> Connections = new();
    private readonly Tachyon _server;
    
    public ConnectionManager(Tachyon server)
    {
        _server = server;
    }
}