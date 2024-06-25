using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Server.Network.Binary;
using Server.Network.Packet;
using Server.Network.Packet.Registry;

namespace Server.Network.Netty.Codec;

public class PacketEncoder(PacketRegistry registry) : MessageToByteEncoder<IServerPacket>
{
    protected override void Encode(IChannelHandlerContext context, IServerPacket message, IByteBuffer output)
    {
        var packetId = registry.GetServerPacketId(message.GetType());
        if (packetId == null)
        {
            Tachyon.LOGGER.Error("Encoder received an unknown to the packet registry packet! Cannot encode!");
            return;
        }
        
        output.WriteVarInt(packetId.Value);
        message.Write(new BinaryBuffer(output));
    }
}