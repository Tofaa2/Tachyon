using Tachyon.Entity;

namespace Tachyon.Network.Connection;

public interface IPlayerProvider
{
    
    internal static readonly IPlayerProvider Default = new DefaultPlayerProvider();

    Player Provide(PlayerConnection connection, string username, Guid uuid);

}

internal class DefaultPlayerProvider : IPlayerProvider
{

    public Player Provide(PlayerConnection connection, string username, Guid uuid)
    {
        return new Player(uuid, username, connection);
    }

}