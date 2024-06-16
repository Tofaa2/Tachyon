using DotNetty.Buffers;
using DotNetty.Common.Utilities;
using DotNetty.Transport.Channels;
using Tachyon.Network.Packet;
using Tachyon.Network.Packet.Processor;

namespace Tachyon.Network;

public class PlayerConnection : SimpleChannelInboundHandler<IPacket>
{
    
    public static readonly AttributeKey<ConnectionState> CONNECTION_STATE_ATTRIBUTE = AttributeKey<ConnectionState>.NewInstance("connection-state");


    internal volatile ConnectionState _connectionState;
    private volatile PacketProcessor _packetProcessor;

    private IChannel _channel;


    private volatile IByteBuffer _tickBuffer = Unpooled.DirectBuffer();
    private object _tickBufferLock = new();
    
    public void SendPacket(IPacket packet)
    {
        lock (_tickBufferLock)
        {
            if (_tickBuffer.ReferenceCount > 0)
            {
                PacketFraming.WriteFramedPacket(_tickBuffer, packet);
            }
        }
    }

    public void SendPacketNow(IPacket packet)
    {
        WriteAndFlush(packet);
    }

    private void WriteAndFlush(IPacket packet)
    {
        WriteWaitingPackets();
        var future = _channel.WriteAndFlushAsync(packet);
    }

    public void Disconnect()
    {
        if (_channel.Open)
        {
            _channel.CloseAsync();
        }
    }

    private void Flush()
    {
        var bufferSize = _tickBuffer.WriterIndex;
        if (bufferSize < 0 || !_channel.Active) return;
        WriteWaitingPackets();
        _channel.Flush();
    }

    private void WriteWaitingPackets()
    {
        if (_tickBuffer.WriterIndex == 0) return;
        IByteBuffer? copy = null;
        lock (_tickBufferLock)
        {
            if (_tickBuffer.ReferenceCount <= 0) return;
            copy = _tickBuffer;
            _tickBuffer = _tickBuffer.Allocator.Buffer(_tickBuffer.WriterIndex);
        }

        var task = _channel.WriteAsync(new FramedPacket(copy));
        task.ContinueWith(t =>
        {
            copy.Release();
        });
    }
    
    public void SetConnectionState(ConnectionState state)
    {
        _connectionState = state;
        if (state == ConnectionState.HANDSHAKE)
        {
            _packetProcessor = PacketProcessor.Handshake(this);
        }
        else if (state == ConnectionState.STATUS)
        {
            _packetProcessor = PacketProcessor.Status(this);
        }
    }

    public override void ChannelInactive(IChannelHandlerContext context)
    {
        lock (_tickBufferLock)
        {
            _tickBuffer.Release();
        }
    }

    public override void ChannelActive(IChannelHandlerContext context)
    {
        base.ChannelActive(context);
        _channel = context.Channel;
        _channel.Configuration.AutoRead = true;
    }

    protected override void ChannelRead0(IChannelHandlerContext ctx, IPacket msg)
    {
        if (!_channel.Open) return;
        try
        {
            Console.WriteLine("reading packet " + msg.GetType());
            _packetProcessor.Process(msg);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }
}