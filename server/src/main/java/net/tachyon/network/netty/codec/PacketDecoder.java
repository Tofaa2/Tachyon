package net.tachyon.network.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.tachyon.network.netty.packet.InboundPacket;
import net.tachyon.utils.Utils;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) {
        if (buf.readableBytes() > 0) {
            final int packetId = Utils.readVarInt(buf);
            list.add(new InboundPacket(packetId, buf));
        }
    }
}
