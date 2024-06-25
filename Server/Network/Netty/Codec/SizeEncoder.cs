using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Server.Network.Binary;

namespace Server.Network.Netty.Codec;

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
        for (var i = 1; i < 5; i++)
        {
            if ((input & (-1 << i * 7)) != 0) continue;
            return i;
        }
        return 5;
    }
}