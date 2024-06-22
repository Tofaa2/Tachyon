using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;
using Tachyon.Network.Packet;
using Tachyon.Network.Packet.Registry;

namespace Tachyon.Network.Netty.Codec;

public class PacketEncoder(PacketRegistry registry) : MessageToByteEncoder<IServerPacket>
{
    protected override void Encode(IChannelHandlerContext context, IServerPacket message, IByteBuffer output)
    {
        var packetId = registry.GetServerPacketId(message.GetType());
        if (packetId == null)
        {
            Console.WriteLine("Encoder received an unknown to the packet registry packet! Cannot encode!");
            return;
        }
        
        output.WriteVarInt(packetId.Value);
        message.Write(new BinaryBuffer(output));
    }
}