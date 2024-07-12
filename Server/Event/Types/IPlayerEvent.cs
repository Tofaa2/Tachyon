namespace Server.Event.Types;

public interface IPlayerEvent : IEvent
{
    
    public Entity.Player Player { get; }
    
}