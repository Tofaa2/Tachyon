
using Tachyon.Network;
using Tachyon.Network.Netty;

namespace Tachyon;

public readonly struct TachyonServer
{

    public static readonly int PROTOCOL_VERSION = 766;
    public static readonly string SERVER_VERSION = "1.20.6";

    public readonly TachyonConfig Config;
    public readonly ConnectionManager ConnectionManager;
    public readonly NettyServer NettyServer;

    public TachyonServer(TachyonConfig config)
    {
        Config = config;
        ConnectionManager = new ConnectionManager(this);
        NettyServer = new NettyServer(this);
    }
    
    public void Init() { 
        Console.WriteLine("Init called");
        NettyServer.Init();
    }

    public void Start()
    {
        NettyServer.Start();
    }

    public void Stop() {
        NettyServer.Stop();
    }

}


public record TachyonConfig(

    int WorkerThreadCount,
    string Host,
    int Port,
    int MaxPlayers,
    bool HideOnline,
    int PacketSizeLimit
)
{ }