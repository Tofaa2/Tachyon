using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;
using Tachyon.Network.Connection;
using Tachyon.Network.Packet;
using Tachyon.Network.Packet.Registry;
using Tachyon.Network.Packet.Type.Handshake.Client;

namespace Tachyon.Network.Netty.Codec;

public class PacketDecoder(PacketRegistry registry, PlayerConnection connection) : ByteToMessageDecoder
{

    
    protected override void Decode(IChannelHandlerContext context, IByteBuffer buf, List<object> output)
    {
        if (buf.ReadableBytes == 0) return;
        BinaryBuffer buffer = new BinaryBuffer(buf);
        var id = buffer.Read(BinaryBuffer.VAR_INT);
        IClientPacket? packet = registry.CreateClientPacket(connection.ConnectionState, id);
        
        if (packet == null)
        {
            Console.WriteLine($"Skipping packet with state {connection.ConnectionState} and ID {id} because a packet object was not found");
            buf.SkipBytes(buf.ReadableBytes);
            return;
        }

        Console.WriteLine("Incoming packet of type " + packet.GetType());

        packet.Read(buffer);

        if (buf.ReadableBytes != 0)
        {
            Console.WriteLine($"More bytes from packet {packet.GetType().Name} ({buf.ReadableBytes})");
        }

        output.Add(packet);
    }
}