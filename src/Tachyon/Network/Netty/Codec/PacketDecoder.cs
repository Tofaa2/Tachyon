using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;
using Tachyon.Network.Packet;

namespace Tachyon.Network.Netty.Codec;

public class PacketDecoder(PlayerConnection connection) : ByteToMessageDecoder
{
    protected override void Decode(IChannelHandlerContext context, IByteBuffer buf, List<object> output)
    {
        if (buf.ReadableBytes == 0) return;
        var id = buf.ReadVarInt();
        var state = connection._connectionState;
        var packet = PacketRegistry.CreateClientPacket(state, id, buf);
        if (packet == null)
        {
            Console.WriteLine("Skipping packet with id " + id + " because packet object for it was not found.");
            buf.SkipBytes(buf.ReadableBytes);
            return;
        }

        if (buf.ReadableBytes != 0)
        {
            Console.WriteLine($"{packet}  has {buf.ReadableBytes} bytes left after reading");
        }
        output.Add(packet);
    }
}