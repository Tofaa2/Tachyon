using Server.Network.Connection;
using Server.Util;

namespace Server.Network.Packet.Registry;

public class PacketRegistry
{

    private readonly PacketSupplier[] _suppliers = new PacketSupplier[Enum.GetValues<ConnectionState>().Length];

    public PacketRegistry()
    {
        Check.PostInit("Packet Registry constructor");
        _suppliers[(int)ConnectionState.Handshake] = new HandshakePacketSupplier();
        _suppliers[(int)ConnectionState.Status] = new StatusPacketSupplier();
        _suppliers[(int)ConnectionState.Login] = new LoginPacketSupplier();
        _suppliers[(int)ConnectionState.Configuration] = new ConfigurationPacketSupplier();
        _suppliers[(int)ConnectionState.Play] = new PlayPacketSupplier();
    }
    
    public int? GetServerPacketId<T>(ConnectionState connectionState) where T : IPacket
    {
        return GetServerPacketId(connectionState, typeof(T));
    }
    
    public int? GetServerPacketId(ConnectionState state, System.Type type)
    {
        return _suppliers[(int)state].GetServerPacketId(type);
    }

    public IClientPacket? CreateClientPacket(ConnectionState state, int packetId)
    {
        return _suppliers[(int)state].GetClientPacket(packetId);
    }

}