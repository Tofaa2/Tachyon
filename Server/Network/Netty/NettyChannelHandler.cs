using DotNetty.Transport.Channels;
using Server.Network.Connection;
using Server.Network.Packet;

namespace Server.Network.Netty;

public class NettyChannelHandler(PlayerConnection connection) : SimpleChannelInboundHandler<IClientPacket>
{


    public override void HandlerAdded(IChannelHandlerContext context)
    {
        Tachyon.LOGGER.Info("Handler Added");
        Tachyon.ConnectionManager.Connections[context.Channel] = connection;
    }

    public override void HandlerRemoved(IChannelHandlerContext context)
    {
        Tachyon.LOGGER.Info("Handler Removed");
        Tachyon.ConnectionManager.Connections.Remove(context.Channel, out var _conn);
    }

    
    
    protected override void ChannelRead0(IChannelHandlerContext ctx, IClientPacket msg)
    {
        connection?.ProcessPacket(msg);
    }
}