using System.Globalization;
using System.Net;
using DotNetty.Handlers.Timeout;
using DotNetty.Transport.Bootstrapping;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;
using Tachyon.Network.Connection;
using Tachyon.Network.Netty.Codec;
using Tachyon.Network.Packet.Registry;
using Tachyon.Util;

namespace Tachyon.Network.Netty;

public class NettyServer
{

    private readonly PacketRegistry _packetRegistry;
    
    private Task<IChannel> _task;
    private IEventLoopGroup _bossGroup, _workerGroup;
    private ServerBootstrap _bootstrap;
    private IChannel _channel;
    
    
    public NettyServer(PacketRegistry packetRegistry)
    {
        Check.PostInit("Netty Server constructor");
        _packetRegistry = packetRegistry;
    }

    public void Init()
    {
        _bossGroup = new MultithreadEventLoopGroup();
        _workerGroup = new MultithreadEventLoopGroup(); // Todo thread count
        
        _bootstrap = new ServerBootstrap()
            .Group(_bossGroup, _workerGroup)
            .Channel<TcpServerSocketChannel>()
            .ChildOption(ChannelOption.TcpNodelay, true)
            .ChildHandler(new ActionChannelInitializer<IChannel>(channel =>
            {

                var pipeline = channel.Pipeline;
                var connection = new PlayerConnection(_packetRegistry, channel);
                pipeline
                    .AddLast("size-decoder", new SizeDecoder())
                    .AddLast("packet-decoder", new PacketDecoder(_packetRegistry, connection))
                    .AddLast("size-encoder", new SizeEncoder())
                    .AddLast("packet-encoder", new PacketEncoder(_packetRegistry))
                    .AddLast("handler", new NettyChannelHandler(connection));

                connection.INTERNAL_SwitchConnectionState(ConnectionState.Handshake);
            }));
    }

    public void Start(IPAddress address, int port)
    {
        _task = _bootstrap.BindAsync(address, port);
        _task.ContinueWith(t =>
        {
            if (t.IsFaulted)
            {
                Console.WriteLine("Failed to start server: " + t.Exception);
                return;
            }
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