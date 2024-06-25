namespace Server.Util;

public class CompletableFuture<T>
{

    private volatile object? _value;
    private Task<T>? _task;
    private Action<T>? _whenComplete;
    
    public CompletableFuture()
    {
    }
    
    public CompletableFuture(T value)
    {
        _value = value;
    }

    public CompletableFuture<T> WhenComplete(Action<T> action)
    {
        _whenComplete = action;
        if (IsCompleted)
        {
            action(Get());
        }

        return this;
    }
    
    public bool IsCompleted => _value != null;
    
    public void Complete(T value)
    {
        Interlocked.Exchange(ref _value, value);
        _whenComplete?.Invoke(value);
    }
    
    

    public T Get()
    {
        if (!IsCompleted)
        {
            throw new InvalidOperationException("Future not completed yet");
        }

        return (T)_value;
    }
    
}