using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;
using Tachyon.Network.Connection;

namespace Tachyon.Network.Netty.Codec;

public class PacketDecoder(Tachyon server, PlayerConnection connection) : ByteToMessageDecoder
{

    
    protected override void Decode(IChannelHandlerContext context, IByteBuffer buf, List<object> output)
    {
        if (buf.ReadableBytes == 0) return;
        var id = buf.ReadVarInt();
        
        var state = connection.ConnectionState;

        var packet = server.PacketRegistry.CreateClientPacket(state, id);
        if (packet == null)
        {
            Console.WriteLine("Skipping packet with id " + id + " as it is not registered in the packet registry!");
            buf.SkipBytes(buf.ReadableBytes);
            return;
        }

        packet.Read(buf);
        if (buf.ReadableBytes > 0)
        {
            Console.WriteLine("Packet " + packet.GetType().Name + " did not read all bytes! Remaining: " + buf.ReadableBytes);
        }
        output.Add(packet);
    }
}