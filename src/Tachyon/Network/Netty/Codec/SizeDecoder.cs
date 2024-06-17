using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Netty.Codec;

public class SizeDecoder : ByteToMessageDecoder
{
    protected override void Decode(IChannelHandlerContext context, IByteBuffer buf, List<object> output)
    {
        buf.MarkReaderIndex();
        var buffer = new byte[3];
        for (int i = 0; i < buffer.Length; i++)
        {
            if (!buf.IsReadable())
            {
                buf.ResetReaderIndex();
                return;
            }

            buffer[i] = buf.ReadByte();
            int length = Unpooled.WrappedBuffer(buffer).ReadVarInt();
            if (buf.ReadableBytes < length)
            {
                buf.ResetReaderIndex();
                return;
            }
            output.Add(buf.ReadBytes(length));
        }
    }
}