using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Utils;

namespace Tachyon.Network.Netty.Codec;

public partial class FramingDecoder : ByteToMessageDecoder
{
    
    protected override void Decode(IChannelHandlerContext context, IByteBuffer input, List<object> output)
    {
        input.MarkReaderIndex();
        
        for (int i = 0; i < 3; ++i) {
            if (!input.IsReadable()) {
                input.ResetReaderIndex();
                return;
            }
            byte b = input.ReadByte();

            if (b >= 0) {
                input.ResetReaderIndex();
                int packetSize = BufUtil.readVarInt(input);

                // Max packet size check
                // if (packetSize >= Tachyon.getServer().getPacketSizeLimit()) {
                //     final PlayerConnection playerConnection = packetProcessor.getPlayerConnection(ctx);
                //     if (playerConnection != null) {
                //         final String identifier = playerConnection.getIdentifier();
                //         LOGGER.warn("An user ({}) sent a packet over the maximum size ({})",
                //             identifier, packetSize);
                //     } else {
                //         LOGGER.warn("An unregistered user sent a packet over the maximum size ({})", packetSize);
                //     }
                //     ctx.close();
                // }

                if (input.ReadableBytes < packetSize) {
                    input.ResetReaderIndex();
                    return;
                }

                output.Add(input.ReadRetainedSlice(packetSize));
                return;
            }
        }

        throw new CorruptedFrameException("length wider than 21-bit");
    }
}