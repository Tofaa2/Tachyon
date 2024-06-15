using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Netty.Codec;

public class ClientChannel(TachyonServer _server) : SimpleChannelInboundHandler<InboundPacket>
{
    
    private PlayerConnection? _connection;
    
    public override void ChannelActive(IChannelHandlerContext context)
    { 
        _connection = _server.ConnectionManager.CreateConnection(context);
    }

    protected override void ChannelRead0(IChannelHandlerContext ctx, InboundPacket msg)
    {
        if (_connection == null)
        {
            ctx.CloseAsync();
            return; // Should never happen;
        }
        if (!_connection.Online)
        {
            _connection.Disconnect();
            return;
        }

        var packetId = msg.Id;
        try
        {
            NetworkBuffer reader = new NetworkBuffer(msg.body);
            var packet = _server.NettyServer.PacketFactory.CreateAndRead(_connection.ConnectionState, packetId, reader);
            Console.WriteLine(packet.GetType());
            var processor = _connection.PacketProcessor;
            processor.Handle(packet);

        }
        finally
        {
            var body = msg.body;
            var availableBytes = body.ReadableBytes;
            if (availableBytes > 0)
            {
                Console.WriteLine("WARNING: Could not fully read packet. Skipping remaining bytes. (packet 0x" + packetId.ToString("X4") + ")");
                body.SkipBytes(availableBytes);
            }
        }
    }

    public override void ChannelInactive(IChannelHandlerContext context)
    {
        var connection = _server.ConnectionManager.RemoveConnection(context);
        if (connection == null) return;
        connection.Internal_UpdateOnlineStatus(false);
        var tickBuffer = connection.TickBuffer;
        lock (tickBuffer)
        {
            tickBuffer.Release();
        }
    }

    public override void ExceptionCaught(IChannelHandlerContext context, Exception exception)
    {
        if (!context.Channel.Active)
        {
            return;
        }
        context.CloseAsync();
    }
}