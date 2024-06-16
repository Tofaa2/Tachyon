namespace Tachyon.Network.Packet.Processor;

public abstract class PacketProcessor
{

    public static PacketProcessor Handshake(PlayerConnection conn) => new Handshake(conn);
    public static PacketProcessor Status(PlayerConnection conn) => new Status(conn);
    
    protected readonly PlayerConnection _connection;

    protected PacketProcessor(PlayerConnection connection)
    {
        _connection = connection;
    }
    
    public abstract void Process(IPacket packet);

    public void OnDisable()
    {
        // Nothing by default.
    }

}