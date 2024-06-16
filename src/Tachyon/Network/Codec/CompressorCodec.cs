using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Codec;

public class PacketCompressor : MessageToByteEncoder<IByteBuffer>
{
    protected override void Encode(IChannelHandlerContext context, IByteBuffer message, IByteBuffer output)
    {
        var uncompressedSize = message.ReadableBytes;
        
        // TODO: Compression
        output.WriteVarInt(uncompressedSize);
    }
}