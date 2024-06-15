using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Utils;

namespace Tachyon.Network.Netty.Codec;

public partial class FramingEncoder : MessageToByteEncoder<IByteBuffer>
{
    protected override void Encode(IChannelHandlerContext context, IByteBuffer message, IByteBuffer output)
    {
        BufUtil.frameBuffer(message, output);
    }
}