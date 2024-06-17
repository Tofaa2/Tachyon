using DotNetty.Transport.Channels;
using Tachyon.Network.Packet;

namespace Tachyon.Network.Netty;

public class NettyChannelHandler(Tachyon server, PlayerConnection connection) : SimpleChannelInboundHandler<IClientPacket>
{


    public override void HandlerAdded(IChannelHandlerContext context)
    {
        Console.WriteLine("Handler added");
        server.ConnectionManager.Connections[context.Channel] = connection;
    }

    public override void HandlerRemoved(IChannelHandlerContext context)
    {
        Console.WriteLine("Handler removed");
        server.ConnectionManager.Connections.Remove(context.Channel, out var _conn);
    }

    
    
    protected override void ChannelRead0(IChannelHandlerContext ctx, IClientPacket msg)
    {
        connection?.ProcessPacket(msg);
    }
}