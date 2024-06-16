using Tachyon.Network;
using Tachyon.Network.Netty;
using Tachyon.Network.Packet;

namespace Tachyon;

public class Tachyon
{

    public ConnectionManager ConnectionManager { get; private set; }
    public NettyServer NettyServer { get; private set; }


    public void Init()
    {
        PacketRegistry.Init();
        ConnectionManager = new ConnectionManager();
        NettyServer = new NettyServer();
        NettyServer.Init();
    }

    public void Start()
    {
        NettyServer.Start();
        while (true)
        {
            string? s = Console.ReadLine();
            if (s == "stop")
            {
                NettyServer.Stop();
                break;
            }
        }
    }
    
    internal static void Main(string[] args)
    {
        Tachyon t = new();
        t.Init();
        t.Start();

    }



}