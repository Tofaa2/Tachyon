using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Packet;
using Tachyon.Utils;

namespace Tachyon.Network.Netty.Codec;

public class PacketEncoder : MessageToByteEncoder<IServerPacket>
{
    protected override void Encode(IChannelHandlerContext context, IServerPacket message, IByteBuffer output)
    {
        BufUtil.writePacket(output, message);
    }
}