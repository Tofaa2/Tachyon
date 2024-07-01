using DotNetty.Transport.Channels;
using Server.Chat.Text;
using Server.Network.Packet;
using Server.Network.Packet.Processor;
using Server.Network.Packet.Registry;
using Server.Network.Packet.Type.Configuration.Server;
using Server.Network.Packet.Type.Login.Server;

namespace Server.Network.Connection;

public class PlayerConnection
{

    private readonly PacketRegistry _packetRegistry;
    private readonly IChannel _channel;

    private volatile ConnectionState _state = ConnectionState.Handshake;
    private volatile PacketProcessor? _processor;
    public ConnectionState ConnectionState => _state;
    
    private string _username = string.Empty;
    private Guid _uuid = Guid.Empty;
    public string Username => _username;
    public Guid Uuid => _uuid;
    
    public int Latency { get; private set; }
    public long LastKeepAlive { get; private set; }
    public bool AnsweredKeepAlive { get; private set; }

    public bool Online { get; private set; } = true;
    
    public void INTERNAL_SetUserData(string username, Guid uuid)
    {
        _username = username;
        _uuid = uuid;
    }
    
    public PlayerConnection(PacketRegistry packetRegistry, IChannel channel)
    {
        _packetRegistry = packetRegistry;
        _channel = channel;
    }



    public bool ProcessKeepAlive(long id)
    {
        if (LastKeepAlive != id)
        {
            Disconnect(IComponent.Text("Bad KeepAlive packet!", NamedTextColor.Red));
            return false;
        }
        AnsweredKeepAlive = true;
        int latency = (int) (DateTimeOffset.UtcNow.ToUnixTimeMilliseconds() - id);
        Latency = latency;
        return true;
    }
    
    public void Disconnect(IComponent? reason = null)
    {
        Online = false;
        if (reason != null)
        {
            switch (_state)
            {
                case ConnectionState.Login:
                    SendPacketNow(new ServerLoginDisconnectPacket(reason));
                    break;
                case ConnectionState.Configuration:
                    SendPacketNow(new ServerConfigurationDisconnectPacket(reason));
                    break;
            }
        } 
        _channel.CloseAsync();
    }
    
    
    public void SendPacketNow(IServerPacket packet)
    {
        _channel.WriteAndFlushAsync(packet).Wait();
    }
    
    public void INTERNAL_SwitchConnectionState(ConnectionState state)
    {
        _state = state;
        switch (state)
        {
            case ConnectionState.Handshake:
                _processor = PacketProcessor.Handshake(_packetRegistry, this);
                break;
            case ConnectionState.Configuration:
                _processor = PacketProcessor.Configuration(_packetRegistry, this);
                break;
            case ConnectionState.Login:
                _processor = PacketProcessor.Login(_packetRegistry, this);
                break;
            case ConnectionState.Play:
                _processor = PacketProcessor.Play(_packetRegistry, this);
                break;
            case ConnectionState.Status:
                _processor = PacketProcessor.Status(_packetRegistry, this);
                break;
        }
    }

    public void ProcessPacket(IClientPacket packet)
    {
        Tachyon.LOGGER.Info($"Processing packet {packet.GetType().Name}");
        _processor?.Process(packet);
    }
    
}