using Tachyon.Network;

namespace Tachyon.Entity;

public class Player : Entity
{
    
    public readonly string Username;
    public readonly PlayerConnection Connection;
    
    
    public Player(Guid uuid, string username, PlayerConnection connection) : base(uuid)
    {
        Username = username;
        Connection = connection;
    }
}