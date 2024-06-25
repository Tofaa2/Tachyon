using System.Net;
using Server.Network.Connection;
using Server.Network.Netty;
using Server.Network.Packet.Registry;

namespace Server;

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