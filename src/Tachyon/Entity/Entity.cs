namespace Tachyon.Entity;

public class Entity
{

    private static volatile int _nextEntityId = 1;
    
    public readonly Guid Uuid;
    public readonly int EntityId;

    public Entity(Guid uuid)
    {
        Uuid = uuid;
        EntityId = Interlocked.Increment(ref _nextEntityId);
    }
    
}