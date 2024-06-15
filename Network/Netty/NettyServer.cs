using System.Net;
using DotNetty.Transport.Bootstrapping;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;
using Tachyon.Network.Netty.Codec;
using Tachyon.Network.Registry;

namespace Tachyon.Network.Netty;

public class NettyServer(TachyonServer _server)
{


    private IEventLoopGroup _boss, _worker;
    private TcpServerSocketChannel _serverChannel;
    private ServerBootstrap _bootstrap;
    private bool _initialized;
    private Task _task;
    public PacketFactory PacketFactory { get; private set; }

    public void Init()
    {
        if (_initialized) throw new Exception("Server already initialized");
        _initialized = true;
        _boss = new MultithreadEventLoopGroup(1);
        _worker = new MultithreadEventLoopGroup(10);
        _bootstrap = new ServerBootstrap()
            .Channel<TcpServerSocketChannel>()
            .Group(_boss, _worker)
            .ChildHandler(new ActionChannelInitializer<ISocketChannel>(channel =>
            {
                var pipeline = channel.Pipeline;

                pipeline.AddLast("framer-decoder", new FramingDecoder());
                pipeline.AddLast("framer-encoder", new FramingEncoder());
                
                pipeline.AddLast("decoder", new PacketDecoder());
                pipeline.AddLast("encoder", new PacketEncoder());
                pipeline.AddLast("client-channel", new ClientChannel(_server));
            }));
        PacketFactory = new PacketFactory(this);
    }

    public void Start()
    {
        var future = _bootstrap.BindAsync(IPAddress.Any, 25565);
        
        if (!future.Wait(TimeSpan.FromSeconds(5)))
        {
            throw new Exception("Failed to bind to port 25565");
        }
        _serverChannel = (TcpServerSocketChannel)future.Result;
    }

    public void Stop()
    {
        try
        {
            _serverChannel.CloseAsync().Wait();
            _worker.ShutdownGracefullyAsync();
            _boss.ShutdownGracefullyAsync();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
        }
    }


}