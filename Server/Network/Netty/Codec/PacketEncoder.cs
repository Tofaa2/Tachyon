using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Channels;
using Server.Network.Binary;
using Server.Network.Connection;
using Server.Network.Packet;
using Server.Network.Packet.Registry;

namespace Server.Network.Netty.Codec;

public class PacketEncoder(PlayerConnection conn, PacketRegistry registry) : MessageToByteEncoder<IServerPacket>
{
    protected override void Encode(IChannelHandlerContext context, IServerPacket message, IByteBuffer output)
    {
        var packetId = registry.GetServerPacketId(conn.ConnectionState, message.GetType());
        if (packetId == null)
        {
            Tachyon.Logger.Error("Encoder received an unknown to the packet registry packet! Cannot encode!");
            return;
        }
        
        output.WriteVarInt(packetId.Value);
        message.Write(new BinaryBuffer(output));
    }
}