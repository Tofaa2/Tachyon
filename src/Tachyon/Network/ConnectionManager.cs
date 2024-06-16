using System.Collections.Concurrent;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;

namespace Tachyon.Network;

public class ConnectionManager
{
    
    public readonly ConcurrentDictionary<ISocketChannel, PlayerConnection> Connections = new();
    
}