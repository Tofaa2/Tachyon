using System.Collections.ObjectModel;

namespace Server.Event;

public interface IEventNode<T> where T : IEvent
{

    public static IEventNode<C> Create<C>(string name, int priority = 0, Func<C, bool>? filter = null) where C : IEvent
    {
        return new EventNodeImpl<C>(priority, name, filter);
    }
    
    public int Priority { get; set; }
    
    public void AddListener<TEvent>(Action<TEvent> handler) where TEvent : T;
    
    public void RemoveListener<TEvent>(Action<TEvent> handler) where TEvent : T;
    
    public void Call<TEvent>(TEvent e) where TEvent : T;
    
    public void CallCancellable<TEvent>(TEvent e, Action<TEvent> ifNotCancelled) where TEvent : T;
    
    public IEventNode<T>? Parent { get; internal set; }
    
    public ReadOnlyCollection<IEventNode<T>> Children { get; }
    
    public void AddChild(IEventNode<T> child);
    
    public void RemoveChild(IEventNode<T> child);
    
    public string Name { get; }
    
}