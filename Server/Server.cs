using System.Net;
using System.Reflection;
using Server.Attribute;
using Server.Event;
using Server.Event.Server;
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
    public IEventNode<IEvent> EventHandler { get; private set; }

    internal void Init()
    {
        EventHandler = IEventNode<IEvent>.Create<IEvent>("Root");
        Packets = new PacketRegistry();
        Connection = new ConnectionManager();
        Netty = new NettyServer(Packets);
        Netty.Init();
        InitRegistries();
    }

    private static void InitRegistries()
    {
        ITrimMaterial.Init();
        var array = new[]
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
        EventHandler.Call(new ServerStartEvent());
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
        EventHandler.Call(new ServerStopEvent());
        Netty.Stop();
    }
}