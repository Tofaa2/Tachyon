using System.Globalization;
using Server.Chat.Text;
using Server.Event.Ping;
using Server.Event.Player;

namespace Server.Impl;

public static class EventListeners
{
    
    private static readonly IFormatProvider FormatProvider = new DateTimeFormatInfo();

    internal static void OnServerListPing(ServerListPingEvent e)
    {
        var now = DateTime.Now;
        var fancyTime = now.ToString("HH:mm:ss", FormatProvider);
        var nowLong = now.ToFileTimeUtc();
        var red = (byte) (nowLong % 255);
        var green = (byte) ((nowLong >> 8) % 255);
        var blue = (byte) ((nowLong >> 16) % 255);
        e.Response.Motd = IComponent.Text(fancyTime, TextColor.FromRgb(red, green, blue));
    }
    
    internal static void OnPlayerPacketReceive(PlayerPacketReceiveEvent e)
    {
    }
    
}