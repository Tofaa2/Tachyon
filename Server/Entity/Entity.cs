namespace Server.Entity;

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

    public enum Animation : byte
    {
        SwingMainHand = 0,
        LeaveBed = 1,
        SwingOffHand = 3,
        CriticalEffect = 4,
        MagicCriticalEffect = 5
    }
    
    
}