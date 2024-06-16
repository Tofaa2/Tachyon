using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Netty.Codec;

public class PacketSizeDecoder : ByteToMessageDecoder
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

            byte b = buf.ReadByte();
            buffer[i] = b;
            if (b < 0) continue;

            var length = Unpooled.WrappedBuffer(buffer).ReadVarInt();
            if (buf.ReadableBytes < length)
            {
                buf.ResetReaderIndex();
                return;
            }
            output.Add(buf.ReadBytes(length));
            return;
        }

        Console.WriteLine("Length larger than 21 bits!");
    }
}