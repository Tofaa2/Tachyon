using System.Globalization;
using System.Net;
using Server.Chat.Text;
using Server.Event.Ping;
using Server.Event.Player;


namespace Server.Impl;
internal static class Program
{
    
    internal static void Main(string[] args)
    {
        AddShutdownHook();
        Tachyon.Init();
            
        Tachyon.EventHandler.AddListener<ServerListPingEvent>(EventListeners.OnServerListPing);
        Tachyon.EventHandler.AddListener<PlayerPacketReceiveEvent>(EventListeners.OnPlayerPacketReceive);
            
        Tachyon.Start(IPAddress.Any, 25565);
    }

    private static void AddShutdownHook()
    {
        var domain = AppDomain.CurrentDomain;
        domain.ProcessExit += (sender, args) =>
        {
            Tachyon.Stop();
        };
    }
        
}