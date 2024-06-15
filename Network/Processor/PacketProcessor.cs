using Tachyon.Network.Packet;

namespace Tachyon.Network.Processor;

public abstract class PacketProcessor(TachyonServer _server, PlayerConnection _connection)
{

    public static PacketProcessor Login(TachyonServer server, PlayerConnection connection) => new LoginPacketProcessor(server, connection);
    public static PacketProcessor Configuration(TachyonServer server, PlayerConnection connection) => new ConfigurationPacketProcessor(server, connection);
    public static PacketProcessor Handshake(TachyonServer server, PlayerConnection connection) => new HandshakePacketProcessor(server, connection);
    public static PacketProcessor Status(TachyonServer server, PlayerConnection connection) => new StatusPacketProcessor(server, connection);
    public static PacketProcessor Play(TachyonServer server, PlayerConnection connection) => new PlayPacketProcessor(server, connection);


    public abstract void Handle(IClientPacket packet);
    
    public void OnDisconnect() {}
    
}