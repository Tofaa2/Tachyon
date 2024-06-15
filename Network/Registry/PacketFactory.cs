using Tachyon.Network.Binary;
using Tachyon.Network.Netty;
using Tachyon.Network.Packet;
using Tachyon.Network.Packet.Handshake.Client;

namespace Tachyon.Network.Registry;

public class PacketFactory
{

    private ClientPacketRegistry _statusRegistry;
    private ClientPacketRegistry _loginRegistry;
    private ClientPacketRegistry _configurationRegistry;
    private ClientPacketRegistry _playRegistry;
    private NettyServer _server;
    
    public PacketFactory(NettyServer server)
    {
        _server = server;
        _statusRegistry = new StatusPacketRegistry();
        _loginRegistry = new LoginPacketRegistry();
        _configurationRegistry = new ConfigurationPacketRegistry();
        _playRegistry = new PlayPacketRegistry();
        Console.WriteLine("PacketFactory created");
    }

    public  IClientPacket CreateAndRead(ConnectionState state, int packetId, NetworkBuffer reader)
    {
        IClientPacket packet = null;

        if (state == ConnectionState.HANDSHAKE)
        {
            if (packetId == 0x00)
            {
                packet = new ClientHandshakePacket();
            }
            else
            {
                Console.WriteLine("Invalid packet id for handshake state: " + packetId.ToString("X4"));
            }
        }
        
        switch (state)
        {
            case ConnectionState.HANDSHAKE:
                if (packetId != 0x00)
                    Console.WriteLine("Invalid packet id for handshake state: " + packetId.ToString("X4"));
                packet = new ClientHandshakePacket();
                break;
            case ConnectionState.PLAY:
                packet = _playRegistry.CreatePacket(packetId);
                break;
            case ConnectionState.STATUS:
                packet = _statusRegistry.CreatePacket(packetId);
                break;
            case ConnectionState.LOGIN:
                packet = _loginRegistry.CreatePacket(packetId);
                break;
            case ConnectionState.CONFIGURATION:
                packet = _configurationRegistry.CreatePacket(packetId);
                break;
            default:
                throw new ArgumentOutOfRangeException(nameof(state), state, null);
        }

        if (packet == null)
        {
            Console.WriteLine("Packet is null somehow");
        }
        packet!.Read(reader);
        return (packet);
    }


}