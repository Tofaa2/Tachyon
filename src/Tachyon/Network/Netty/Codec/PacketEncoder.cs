using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;
using Tachyon.Network.Packet;

namespace Tachyon.Network.Netty.Codec;

public class PacketEncoder(Tachyon server) : MessageToByteEncoder<IServerPacket>
{
    protected override void Encode(IChannelHandlerContext context, IServerPacket message, IByteBuffer output)
    {
        var packetId = server.PacketRegistry.GetServerPacketId(message.GetType());
        if (packetId == null)
        {
            Console.WriteLine("Encoder received an unknown to the packet registry packet! Cannot encode!");
            return;
        }
        
        output.WriteVarInt(packetId.Value);
        message.Write(output);
    }
}