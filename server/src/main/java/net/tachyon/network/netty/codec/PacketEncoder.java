package net.tachyon.network.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.utils.PacketUtils;

public class PacketEncoder extends MessageToByteEncoder<ServerPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ServerPacket packet, ByteBuf buf) {
        PacketUtils.writePacket(buf, packet);
    }

}
