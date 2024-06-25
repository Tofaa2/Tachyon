using Server.Network.Packet.Type.Play.Server;

namespace Server.Entity;

public sealed class EntitySpawnType
{

    public static readonly EntitySpawnType BASIC = new(entity =>
    {
        return default!; // TODO
    });

    private Func<Entity, ServerPlaySpawnEntityPacket> _spawner;
    
    public EntitySpawnType(Func<Entity, ServerPlaySpawnEntityPacket> spawner)
    {
        _spawner = spawner;
    }

}