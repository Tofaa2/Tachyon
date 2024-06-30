using System.Net;
using System.Reflection;
using Server.Attribute;
using Server.Item.Armor;
using Server.Item.Material;
using Server.Network.Connection;
using Server.Network.Netty;
using Server.Network.Packet.Registry;
using Server.World.Block;

namespace Server;

internal class Server : IServer
{
    public ConnectionManager Connection { get; private set; }
    public PacketRegistry Packets { get; private set; }
    public NettyServer Netty { get; private set; }

    internal void Init()
    {
        Packets = new PacketRegistry();
        Connection = new ConnectionManager();
        Netty = new NettyServer(Packets);
        Netty.Init();
        InitRegistries();
    }

    private static void InitRegistries()
    {
        ITrimMaterial.Init();
        var array = new Type[]
        {
            typeof(ITrimMaterial),
            typeof(IMaterial),
            typeof(IBlock),
            typeof(IAttribute)
        };
        foreach (var type in array)
        {
            type.GetMethod("Init", BindingFlags.Static | BindingFlags.Public)?.Invoke(null, null);
        }
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