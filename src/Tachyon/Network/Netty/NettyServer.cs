using System.Net;
using DotNetty.Handlers.Timeout;
using DotNetty.Transport.Bootstrapping;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;
using Tachyon.Network.Netty.Codec;

namespace Tachyon.Network.Netty;

public class NettyServer
{

    private ServerBootstrap _bootstrap;
    private IEventLoopGroup _boss, _worker;
    private IChannel _channel;
    private Task _task;
    private Tachyon _server;
    
    public void Init()
    {
        _boss = new MultithreadEventLoopGroup(1);
        _worker = new MultithreadEventLoopGroup(10);
        _bootstrap = new ServerBootstrap()
            .Group(_boss, _worker)
            .LocalAddress(IPAddress.Any, 25565)
            .ChildOption(ChannelOption.TcpNodelay, true)
            .ChildOption(ChannelOption.SoKeepalive, true)
            .Channel<TcpServerSocketChannel>()
            .ChildHandler(new ActionChannelInitializer<ISocketChannel>(channel =>
            {

                var connection = new PlayerConnection();
                channel.Pipeline
                    .AddLast("timeout", new ReadTimeoutHandler(30))
                    .AddLast("size-decoder", new PacketSizeDecoder())
                    .AddLast("packet-decoder", new PacketDecoder(connection))
                    .AddLast("size-encoder", new PacketSizeEncoder())
                    .AddLast("packet-encoder", new PacketEncoder())
                    .AddLast("session", connection);
                _server.ConnectionManager.Connections[channel] = connection;
                Console.WriteLine("Channel handler added to pipeline.");
            }));
    }

    public void Start()
    {
        _task = _bootstrap.BindAsync().ContinueWith(t =>
        {
            if (!t.IsCompletedSuccessfully)
                Console.WriteLine("Failed to start server.");
            _channel = t.Result;
            Console.WriteLine("Server started on port 25565");
        });
    }
    
    public void Stop()
    {
        _channel.CloseAsync().Wait();
        _boss.ShutdownGracefullyAsync().Wait();
        _worker.ShutdownGracefullyAsync().Wait();
    }

}