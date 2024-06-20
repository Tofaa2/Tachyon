using Tachyon.Network.Packet.Registry;

namespace Tachyon.Network.Packet.Processor;

public abstract class PacketProcessor(PacketRegistry packetRegistry, PlayerConnection connection)
{

    public static PacketProcessor Handshake(PacketRegistry packetRegistry, PlayerConnection c) =>
        new HandshakePacketProcessor(packetRegistry, c);
    
    public static PacketProcessor Login(PacketRegistry packetRegistry, PlayerConnection c) =>
        new LoginPacketProcessor(packetRegistry, c);
    
    public static PacketProcessor Play(PacketRegistry packetRegistry, PlayerConnection c) =>
        new PlayPacketProcessor(packetRegistry, c);
    
    public static PacketProcessor Status(PacketRegistry packetRegistry, PlayerConnection c) =>
        new StatusPacketProcessor(packetRegistry, c);
    
    public static PacketProcessor Configuration(PacketRegistry packetRegistry, PlayerConnection c) =>
        new ConfigPacketProcessor(packetRegistry, c);
    
    public abstract void Process(IClientPacket packet);

    public void OnDisconnect()
    {
        // Do nothing by default
    }

}