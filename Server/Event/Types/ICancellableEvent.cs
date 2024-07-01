namespace Server.Event;

public interface ICancellableEvent : IEvent
{
    
    public bool IsCancelled { get; set; }
    
}