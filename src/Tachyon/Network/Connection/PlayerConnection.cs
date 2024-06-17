using DotNetty.Transport.Channels;
using Tachyon.Network.Connection;
using Tachyon.Network.Packet;
using Tachyon.Network.Packet.Processor;

namespace Tachyon.Network;

public class PlayerConnection
{

    private readonly Tachyon _server;
    private readonly IChannel _channel;

    private volatile ConnectionState _state = ConnectionState.Handshake;
    private volatile PacketProcessor? _processor;
    public ConnectionState ConnectionState => _state;
    
    public PlayerConnection(Tachyon server, IChannel channel)
    {
        _server = server;
        _channel = channel;
    }


    public void Disconnect()
    {
        var t = _channel.CloseAsync();
        t.ContinueWith(tg =>
        {
            Console.WriteLine("Disconnected from client");
            
        });
    }
    
    public void SendPacketNow(IServerPacket packet)
    {
        _channel.WriteAndFlushAsync(packet).Wait();
        // tg.ContinueWith(t =>
        // {
        //     if (t.IsCompletedSuccessfully)
        //     {
        //         Console.WriteLine("Sent packet successfully");
        //     }
        //     else
        //     {
        //         Console.WriteLine("Failed to send packet");
        //     }
        //
        //     ;
        // });
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