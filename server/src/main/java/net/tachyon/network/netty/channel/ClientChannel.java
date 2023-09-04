package net.tachyon.network.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.tachyon.MinecraftServer;
import net.tachyon.Tachyon;
import net.tachyon.entity.Player;
import net.tachyon.network.netty.packet.InboundPacket;
import net.tachyon.network.player.NettyPlayerConnection;
import net.tachyon.network.player.PlayerConnection;
import net.tachyon.network.ConnectionManager;
import net.tachyon.network.PacketProcessor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientChannel extends SimpleChannelInboundHandler<InboundPacket> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientChannel.class);

    private final static ConnectionManager CONNECTION_MANAGER = MinecraftServer.getConnectionManager();
    private final PacketProcessor packetProcessor;

    public ClientChannel(@NotNull PacketProcessor packetProcessor) {
        this.packetProcessor = packetProcessor;
    }

    @Override
    public void channelActive(@NotNull ChannelHandlerContext ctx) {
        //System.out.println("CONNECTION");
        packetProcessor.createPlayerConnection(ctx);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, InboundPacket packet) {
        try {
            packetProcessor.process(ctx, packet);
        } catch (Exception e) {
            Tachyon.getServer().getExceptionManager().handleException(e);
        } finally {
            // Check remaining
            final ByteBuf body = packet.getBody();
            final int packetId = packet.getPacketId();

            final int availableBytes = body.readableBytes();

            if (availableBytes > 0) {
                final PlayerConnection playerConnection = packetProcessor.getPlayerConnection(ctx);

                LOGGER.warn("WARNING: Packet 0x{} not fully read ({} bytes left), {}",
                        Integer.toHexString(packetId),
                        availableBytes,
                        playerConnection);

                body.skipBytes(availableBytes);
            }
        }
    }

    @Override
    public void channelInactive(@NotNull ChannelHandlerContext ctx) {
        PlayerConnection playerConnection = packetProcessor.removePlayerConnection(ctx);
        if (playerConnection != null) {
            // Remove the connection
            ((NettyPlayerConnection)playerConnection).refreshOnline(false);
            Player player = playerConnection.getPlayer();
            if (player != null) {
                player.remove();
                CONNECTION_MANAGER.removePlayer(playerConnection);
            }

            // Release tick buffer
            final ByteBuf tickBuffer = ((NettyPlayerConnection) playerConnection).getTickBuffer();
            synchronized (tickBuffer) {
                tickBuffer.release();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (!ctx.channel().isActive()) {
            return;
        }

        Tachyon.getServer().getExceptionManager().handleException(cause);
        ctx.close();
    }
}
