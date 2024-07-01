using System.Net;
using DotNetty.Transport.Bootstrapping;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;
using Server.Network.Connection;
using Server.Network.Netty.Codec;
using Server.Network.Packet.Registry;
using Server.Util;

namespace Server.Network.Netty;

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
                    .AddLast("packet-encoder", new PacketEncoder(connection, _packetRegistry))
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
                Tachyon.LOGGER.Error("Failed to start server: " + t.Exception);
                // free the port
                _bossGroup.ShutdownGracefullyAsync().Wait();
                _workerGroup.ShutdownGracefullyAsync().Wait();
                _bootstrap = null;
                return;
            }
            _channel = t.Result;
            Tachyon.LOGGER.Info("Server started on " + _channel.LocalAddress);
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