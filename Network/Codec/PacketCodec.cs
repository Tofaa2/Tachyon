using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Common.Utilities;
using DotNetty.Transport.Channels;
using Tachyon.Network.Binary;
using Tachyon.Network.Packet;
using Tachyon.Network;
namespace Tachyon.Network.Codec;


public class PacketDecoder : ByteToMessageDecoder
{


    protected override void Decode(IChannelHandlerContext context, IByteBuffer input, List<object> output)
    {
        if (input.ReadableBytes == 0) return;
        var id = input.ReadVarInt();
        IAttribute<ConnectionState> connectionState =
            context.Channel.GetAttribute(PlayerConnection.CONNECTION_STATE_ATTRIBUTE);
        ConnectionState? state = connectionState.Get();
        if (state == null)
        {
            throw new InvalidOperationException("Connection state is null");
        }

        IPacket? packet = PacketRegistry.CreateClientPacket(state, id, input);
        if (packet == null)
        {
            Console.WriteLine(
                $"Skipping packet with state {state} and ID {id} because a packet object was not found");

            input.SkipBytes(input.ReadableBytes);
            return;
        }

        if (input.ReadableBytes != 0)
        {
            Console.WriteLine($"{packet} has more bytes available to read after being fully read!");
        }
        output.Add(packet);
    }
}

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
            Console.WriteLine(e.StackTrace);
        }
    }
}