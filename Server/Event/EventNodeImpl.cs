using System.Collections.ObjectModel;

namespace Server.Event;

internal class EventNodeImpl<T> : IEventNode<T> where T : IEvent
{
    public int Priority { get; set; }
    public string Name { get; }
    public IEventNode<T>? Parent { get; set; }
    
    private readonly List<IEventNode<T>> _children = new();
    public ReadOnlyCollection<IEventNode<T>> Children => _children.AsReadOnly();
    
    private readonly Func<T, bool> _filter;
    
    private readonly Dictionary<Type, HashSet<Action<T>>> _handlers = new();
    
    internal EventNodeImpl(int priority, string name, Func<T, bool>? filter = null)
    {
        Priority = priority;
        Name = name;
        _filter = filter ?? (_ => true);
    }
    
    public void AddChild(IEventNode<T> child)
    {
        _children.Add(child);   
        child.Parent = this;
    }

    public void RemoveChild(IEventNode<T> child)
    {
        _children.Remove(child);
        child.Parent = null;
    }
    
    public void AddListener<TEvent>(Action<TEvent> handler) where TEvent : T
    {
        var type = typeof(TEvent);
        if (!_handlers.ContainsKey(type))
        {
            _handlers[type] = new();
        }

        _handlers[type].Add(x => handler((TEvent)x));
    }

    public void RemoveListener<TEvent>(Action<TEvent> handler) where TEvent : T
    {
        if (_handlers.TryGetValue(typeof(TEvent), out var handlers))
        {
            handlers.Remove(handler as Action<T> ?? throw new ArgumentException("Handler is not of type Action<T>"));
        }
    }

    public void Call<TEvent>(TEvent e) where TEvent : T
    {
        if (_handlers.TryGetValue(typeof(TEvent), out var handlers))
        {
            foreach (var h in handlers.Select(handler => handler as Action<TEvent>))
            {
                
                if (_filter(e))
                {
                    h?.Invoke(e);
                }
            }
        }
        // sort children by priority and call them
        foreach (var child in Children.OrderBy(c => c.Priority))
        {
            child.Call(e);
        }
    }

    public void CallCancellable<TEvent>(TEvent e, Action<TEvent> ifNotCancelled) where TEvent : T
    {
        Call(e);
        if (e is ICancellableEvent c && !c.IsCancelled)
        {
            ifNotCancelled(e);
        }
    }

}