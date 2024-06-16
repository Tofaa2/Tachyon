using System.Collections.Concurrent;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;

namespace Tachyon.Network;

public class ConnectionManager
{
    
    public static readonly ConcurrentDictionary<ISocketChannel, PlayerConnection> Connections = new();
    
}