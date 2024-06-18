using DotNetty.Transport.Channels;
using Tachyon.Network.Connection;
using Tachyon.Network.Packet;
using Tachyon.Network.Packet.Processor;
using Tachyon.Network.Packet.Type.Login.Server;
using Tachyon.Text;

namespace Tachyon.Network;

public class PlayerConnection
{

    private readonly Tachyon _server;
    private readonly IChannel _channel;

    private volatile ConnectionState _state = ConnectionState.Handshake;
    private volatile PacketProcessor? _processor;
    public ConnectionState ConnectionState => _state;
    
    private string _username = string.Empty;
    private Guid _uuid = Guid.Empty;
    public string Username => _username;
    public Guid Uuid => _uuid;


    public bool Online { get; private set; } = true;
    
    public void INTERNAL_SetUserData(string username, Guid uuid)
    {
        _username = username;
        _uuid = uuid;
    }
    
    public PlayerConnection(Tachyon server, IChannel channel)
    {
        _server = server;
        _channel = channel;
    }


    public void Disconnect(IComponent? reason = null)
    {
        Online = false;
        if (reason != null)
        {
            if (_state == ConnectionState.Login)
            {
                SendPacketNow(new ServerLoginDisconnectPacket(reason));
            }
        }
        var t = _channel.CloseAsync();
        t.ContinueWith(tg =>
        {
            Console.WriteLine("Disconnected from client");
        });
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
                _processor = PacketProcessor.Handshake(_server, this);
                break;
            case ConnectionState.Configuration:
                _processor = PacketProcessor.Configuration(_server, this);
                break;
            case ConnectionState.Login:
                _processor = PacketProcessor.Login(_server, this);
                break;
            case ConnectionState.Play:
                _processor = PacketProcessor.Play(_server, this);
                break;
            case ConnectionState.Status:
                _processor = PacketProcessor.Status(_server, this);
                break;
        }
    }

    public void ProcessPacket(IClientPacket packet)
    {
        _processor?.Process(packet);
    }
    
}