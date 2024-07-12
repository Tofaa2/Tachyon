namespace Server.Event;

public interface IEventHandler
{
    
    public IEventNode<IEvent> EventNode { get; }
    
}