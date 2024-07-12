using Server.Network.Connection;

namespace Server;

public interface IServer
{
    
    public ConnectionManager Connection { get; }

}
