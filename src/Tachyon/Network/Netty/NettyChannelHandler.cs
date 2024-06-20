using DotNetty.Transport.Channels;
using Tachyon.Network.Packet;
using Tachyon.Network.Packet.Registry;

namespace Tachyon.Network.Netty;

public class NettyChannelHandler(PlayerConnection connection) : SimpleChannelInboundHandler<IClientPacket>
{


    public override void HandlerAdded(IChannelHandlerContext context)
    {
        Console.WriteLine("Handler added");
        Tachyon.ConnectionManager.Connections[context.Channel] = connection;
    }

    public override void HandlerRemoved(IChannelHandlerContext context)
    {
        Console.WriteLine("Handler removed");
        Tachyon.ConnectionManager.Connections.Remove(context.Channel, out var _conn);
    }

    
    
    protected override void ChannelRead0(IChannelHandlerContext ctx, IClientPacket msg)
    {
        connection?.ProcessPacket(msg);
    }
}