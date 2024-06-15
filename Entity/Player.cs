using System.Xml;
using Tachyon.Network;

namespace Tachyon.Entity;

public class Player
{

    public readonly int EntityId;
    public readonly string Username;
    public readonly UniqueId Uuid;
    public readonly PlayerConnection Connection;

    public Player(PlayerConnection connection, string username, UniqueId uid)
    {
        Connection = connection;
        Username = username;
        Uuid = uid;
        EntityId = 123; // TODO
    }



}
