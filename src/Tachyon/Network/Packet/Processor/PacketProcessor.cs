namespace Tachyon.Network.Packet.Processor;

public abstract class PacketProcessor(Tachyon server, PlayerConnection connection)
{

    public static PacketProcessor Handshake(Tachyon server, PlayerConnection c) =>
        new HandshakePacketProcessor(server, c);
    
    public static PacketProcessor Login(Tachyon server, PlayerConnection c) =>
        new LoginPacketProcessor(server, c);
    
    public static PacketProcessor Play(Tachyon server, PlayerConnection c) =>
        new PlayPacketProcessor(server, c);
    
    public static PacketProcessor Status(Tachyon server, PlayerConnection c) =>
        new StatusPacketProcessor(server, c);
    
    public static PacketProcessor Configuration(Tachyon server, PlayerConnection c) =>
        new ConfigPacketProcessor(server, c);
    
    public abstract void Process(IClientPacket packet);

    public void OnDisconnect()
    {
        // Do nothing by default
    }

}