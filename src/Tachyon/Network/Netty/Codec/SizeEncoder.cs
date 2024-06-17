using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Netty.Codec;

public class SizeEncoder : MessageToByteEncoder<IByteBuffer>
{
    protected override void Encode(IChannelHandlerContext context, IByteBuffer msg, IByteBuffer output)
    {
        var bodyLen = msg.ReadableBytes;
        var headerLen = GetVarIntSize(bodyLen);
        output.EnsureWritable(headerLen + bodyLen);
        output.WriteVarInt(bodyLen);
        output.WriteBytes(msg);
    }


    private static int GetVarIntSize(int input) {
        return (input & 0xFFFFFF80) == 0
            ? 1 : (input & 0xFFFFC000) == 0
                ? 2 : (input & 0xFFE00000) == 0
                    ? 3 : (input & 0xF0000000) == 0
                        ? 4 : 5;
    }
}