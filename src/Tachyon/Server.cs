using System.Net;
using Tachyon.Network.Connection;
using Tachyon.Network.Netty;
using Tachyon.Network.Packet.Registry;

namespace Tachyon;

internal class Server : IServer
{
    public ConnectionManager Connection { get; private set; }
    public PacketRegistry Packets { get; private set; }
    public NettyServer Netty { get; private set; }

    internal void Init()
    {
        Packets = new();
        Connection = new();
        Netty = new(Packets);
        Netty.Init();
    }

    internal void Start(IPAddress address, int port)
    {
        Netty.Start(address, port);
        while (true)
        {
            string? s = Console.ReadLine();
            if (s != "exit") continue;
            Stop();
        }
    }
    
    internal void Stop()
    {
        Netty.Stop();
    }
}