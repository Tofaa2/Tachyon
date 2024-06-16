using System.Runtime.Serialization;

namespace Tachyon.Network;

public sealed class ConnectionState
{
    public static ConnectionState FromId(int id)
    {
        return id switch
        {
            0 => HANDSHAKE,
            1 => STATUS,
            2 => LOGIN,
            3 => PLAY,
            _ => throw new SerializationException("Invalid connection state id")
        };
    }
    
    public static readonly ConnectionState HANDSHAKE = new(0);
    public static readonly ConnectionState STATUS = new(1);
    public static readonly ConnectionState LOGIN = new(2);
    public static readonly ConnectionState PLAY = new(3);

    public readonly int Id;
    
    private ConnectionState(int id)
    {
        Id = id;
    }
    
}