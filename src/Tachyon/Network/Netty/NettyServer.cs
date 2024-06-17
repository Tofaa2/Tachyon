using System.Net;
using DotNetty.Handlers.Timeout;
using DotNetty.Transport.Bootstrapping;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;
using Tachyon.Network.Connection;
using Tachyon.Network.Netty.Codec;

namespace Tachyon.Network.Netty;

public class NettyServer
{

    private readonly Tachyon _server;

    private Task<IChannel> _task;
    private IEventLoopGroup _bossGroup, _workerGroup;
    private ServerBootstrap _bootstrap;
    private IChannel _channel;
    
    public NettyServer(Tachyon server)
    {
        _server = server;
    }

    public void Init()
    {
        _bossGroup = new MultithreadEventLoopGroup();
        _workerGroup = new MultithreadEventLoopGroup(); // Todo thread count
        
        _bootstrap = new ServerBootstrap()
            .Group(_bossGroup, _workerGroup)
            .Channel<TcpServerSocketChannel>()
            .ChildOption(ChannelOption.TcpNodelay, true)
            .LocalAddress(IPAddress.Any, 25565)
            .ChildHandler(new ActionChannelInitializer<IChannel>(channel =>
            {

                var pipeline = channel.Pipeline;
                var connection = new PlayerConnection(_server, channel);
                pipeline
                    .AddLast("size-decoder", new SizeDecoder())
                    .AddLast("packet-decoder", new PacketDecoder(_server, connection))
                    .AddLast("size-encoder", new SizeEncoder())
                    .AddLast("packet-encoder", new PacketEncoder(_server))
                    .AddLast("handler", new NettyChannelHandler(_server, connection));

                connection.INTERNAL_SwitchConnectionState(ConnectionState.Handshake);
            }));
    }

    public void Start()
    {
        _task = _bootstrap.BindAsync();
        _task.ContinueWith(t =>
        {
            _channel = t.Result;
            Console.WriteLine("Server started on " + _channel.LocalAddress);
        });
    }

    public void Stop()
    {
        _channel.CloseAsync().Wait();
        _bossGroup.ShutdownGracefullyAsync().Wait();
        _workerGroup.ShutdownGracefullyAsync().Wait();
        _task.Dispose();
    }

}