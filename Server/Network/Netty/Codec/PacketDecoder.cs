using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Server.Network.Binary;
using Server.Network.Connection;
using Server.Network.Packet;
using Server.Network.Packet.Registry;

namespace Server.Network.Netty.Codec;

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
            Tachyon.Logger.Warn($"Skipping packet with state {connection.ConnectionState} and ID {id} because a packet object was not found");
            buf.SkipBytes(buf.ReadableBytes);
            return;
        }


        packet.Read(buffer);

        if (buf.ReadableBytes != 0)
        {
            Tachyon.Logger.Warn($"More bytes from packet {packet.GetType().Name} ({buf.ReadableBytes})");
        }

        output.Add(packet);
    }
}