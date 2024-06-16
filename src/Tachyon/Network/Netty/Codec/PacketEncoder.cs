using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;
using Tachyon.Network.Packet;

namespace Tachyon.Network.Netty.Codec;

public class PacketEncoder : MessageToByteEncoder<IPacket>
{
    protected override void Encode(IChannelHandlerContext context, IPacket message, IByteBuffer output)
    {
        var id = PacketRegistry.GetServerPacketId(message.GetType());
        try
        {
            output.WriteVarInt(id);
            message.Write(output);
        }
        catch (Exception e)
        {
            Console.WriteLine($"Failed to encode packet {message.GetType().Name} with id {id}");
            Console.WriteLine(e);
        }
    }
}