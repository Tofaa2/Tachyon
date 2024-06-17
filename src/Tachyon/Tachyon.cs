using System.Diagnostics;
using DotNetty.Codecs.Mqtt.Packets;
using Tachyon.Network.Connection;
using Tachyon.Network.Netty;
using Tachyon.Network.Packet.Registry;

namespace Tachyon;

public class Tachyon
{

    public static readonly int ProtocolVersion = 767;
    public static readonly string Version = "1.20.6";

    public ConnectionManager ConnectionManager { get; private set; }
    public PacketRegistry PacketRegistry { get; private set; }
    public NettyServer NettyServer { get; private set; }
    
    public void Init()
    {
        PacketRegistry = new PacketRegistry(this);
        ConnectionManager = new ConnectionManager(this);
        NettyServer = new NettyServer(this);
        NettyServer.Init();
    }

    public void Start()
    {
        NettyServer.Start();
        while (true)
        {
            string? s = Console.ReadLine();
            if (s != "exit") continue;
            Stop();
            break;
        }
    }
    
    public void Stop()
    {
        NettyServer.Stop();
    }

}