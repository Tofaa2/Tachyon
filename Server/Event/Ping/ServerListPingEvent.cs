using Server.Ping;

namespace Server.Event.Ping;

public class ServerListPingEvent : IEvent
{

    public readonly ServerListPingResponse Response = new();

}