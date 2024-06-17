namespace Tachyon.Util;

public class CompletableFuture<T>
{

    private volatile object? _value;
    private Task<T>? _task;
    
    public CompletableFuture()
    {
    }
    
    public CompletableFuture(T value)
    {
        _value = value;
    }
    
    public bool IsCompleted => _value != null;
    
    public void Complete(T value)
    {
        Interlocked.Exchange(ref _value, value);
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