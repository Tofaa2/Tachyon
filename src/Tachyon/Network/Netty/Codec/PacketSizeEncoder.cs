using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Netty.Codec;

public class PacketSizeEncoder : MessageToByteEncoder<IByteBuffer>
{
    protected override void Encode(IChannelHandlerContext context, IByteBuffer message, IByteBuffer output)
    {
        output.WriteVarInt(message.ReadableBytes);
        output.WriteBytes(message);
    }
}