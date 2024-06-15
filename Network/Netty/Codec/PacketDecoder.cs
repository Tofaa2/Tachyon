using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Utils;

namespace Tachyon.Network.Netty.Codec;

public class PacketDecoder : ByteToMessageDecoder
{
    protected override void Decode(IChannelHandlerContext context, IByteBuffer input, List<object> output)
    {
        if (input.ReadableBytes < 1)
        {
            return;
        }

        int packetId = BufUtil.readVarInt(input);
        output.Add(new InboundPacket(packetId, input));
    }
}