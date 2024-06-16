using System.Net;
using DotNetty.Handlers.Timeout;
using DotNetty.Transport.Bootstrapping;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;
using Tachyon.Network.Codec;

namespace Tachyon.Network;

public static class NettyServer
{

    private static ServerBootstrap _bootstrap;
    private static IEventLoopGroup _boss, _worker;
    private static IChannel _channel;
    private static Task _task;
    
    
    public static void init()
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
                    .AddLast("packet-decoder", new PacketDecoder())
                    .AddLast("size-encoder", new PacketSizeEncoder())
                    .AddLast("packet-encoder", new PacketEncoder())
                    .AddLast("session", connection);
                connection.SetConnectionState(ConnectionState.HANDSHAKE);
                ConnectionManager.Connections[channel] = connection;
            }));
    }

    public static void Start()
    {
        _task = _bootstrap.BindAsync().ContinueWith(t =>
        {
            _channel = t.Result;
            Console.WriteLine("Server started on port 25565");
        });
    }

}