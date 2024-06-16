using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Codec;


public class PacketSizeEncoder : MessageToByteEncoder<IByteBuffer>
{
    protected override void Encode(IChannelHandlerContext context, IByteBuffer message, IByteBuffer output)
    {
        output.WriteVarInt(message.ReadableBytes);
        output.WriteBytes(message);
    }
}

public class PacketSizeDecoder : ByteToMessageDecoder
{
    protected override void Decode(IChannelHandlerContext context, IByteBuffer buf, List<object> output)
    {
        buf.MarkReaderIndex();

        var buffer = new byte[3];
        for (int i = 0; i < buffer.Length; i ++) {
            if (!buf.IsReadable())
            {
                buf.ResetReaderIndex();
                return;
            }

            buffer[i] = buf.ReadByte();
            if (buffer[i] < 0) continue;

            var length = Unpooled.WrappedBuffer(buffer).ReadVarInt();
            if (buf.ReadableBytes < length)
            {
                buf.ResetReaderIndex();
                return;
            }

            output.Add(buf.ReadBytes(length));
            return;
        }

        throw new CorruptedFrameException("length wider than 21 bits");
    }
}