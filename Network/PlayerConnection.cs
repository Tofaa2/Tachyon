using System.Collections.Concurrent;
using System.Net;
using System.Net.Sockets;
using DotNetty.Buffers;
using DotNetty.Transport.Channels.Sockets;
using Tachyon.Entity;
using Tachyon.Network.Binary;
using Tachyon.Network.Netty;
using Tachyon.Network.Packet;
using Tachyon.Network.Processor;
using Tachyon.Utils;

namespace Tachyon.Network;

public class PlayerConnection(TachyonServer server, ISocketChannel channel)
{

    public static readonly int FLUSH_SIZE = 20000;

    public readonly IByteBuffer TickBuffer = PooledByteBufferAllocator.Default.DirectBuffer();
    public readonly EndPoint Address = channel.RemoteAddress;
    private volatile ConnectionState _state;
    public ConnectionState ConnectionState
    {
        get => _state;
        set => throw new Exception("State is read-only");
    }

    public bool Online { get; private set; } = true;
    private Player? _player;
    public PacketProcessor PacketProcessor { get; private set; }
    
    public void Internal_UpdateOnlineStatus(bool online)
    {
        Online = online;
    }
    
    public void Disconnect()
    {
        Online = false;
        channel.CloseAsync();
    }
    
    public void SwitchConnectionState(ConnectionState state)
    {
        _state = state;
        PacketProcessor = state switch
        {
            ConnectionState.PLAY => PacketProcessor.Play(server, this),
            ConnectionState.HANDSHAKE => PacketProcessor.Handshake(server, this),
            ConnectionState.LOGIN => PacketProcessor.Login(server, this),
            ConnectionState.CONFIGURATION => PacketProcessor.Configuration(server, this),
            ConnectionState.STATUS => PacketProcessor.Status(server, this),
            _ => PacketProcessor
        };
        Console.WriteLine("Switched packet state to " + state);
    }
    
    public void SendPacket(IServerPacket serverPacket) {
        if (!channel.Active)
            return;

        if (ShouldSendPacket(serverPacket)) {
            if (_player != null) {
                WriteRaw(serverPacket);
                // Flush happen during #update()
                // if (serverPacket instanceof CacheablePacket cacheablePacket && Tachyon.getServer().isPacketCachingEnabled()) {
                //     final UUID identifier = cacheablePacket.getIdentifier();
                //
                //     if (identifier == null) {
                //         // This packet explicitly asks to do not retrieve the cache
                //         write(serverPacket);
                //     } else {
                //         final long timestamp = cacheablePacket.getTimestamp();
                //         // Try to retrieve the cached buffer
                //         TemporaryCache<TimedBuffer> temporaryCache = cacheablePacket.getCache();
                //         TimedBuffer timedBuffer = temporaryCache.retrieve(identifier);
                //
                //         // Update the buffer if non-existent or outdated
                //         final boolean shouldUpdate = timedBuffer == null ||
                //                                      timestamp > timedBuffer.getTimestamp();
                //
                //         if (shouldUpdate) {
                //             final ByteBuf buffer = PacketUtils.createFramedPacket(serverPacket, false);
                //             timedBuffer = new TimedBuffer(buffer, timestamp);
                //         }
                //
                //         temporaryCache.cache(identifier, timedBuffer);
                //         write(new FramedPacket(timedBuffer.getBuffer()));
                //     }
                //
                // } else {
                //     write(serverPacket);
                // }
            } else {
                // Player is probably not logged yet
                WriteAndFlush(serverPacket);
            }
        }
    }

    public void WriteAndFlush(object data)
    {
        WriteWaitingPackets();
        var future = channel.WriteAndFlushAsync(data);
    }
    
    private bool ShouldSendPacket(IServerPacket packet)
    {
        return true; // TODO:
    }

    public void Tick()
    {
        if (channel.Active)
        {
            WriteWaitingPackets();
            channel.Flush();
        }
        
        // TODO: Rate limitations
    }

    public void WriteRaw(object data)
    {
        switch (data)
        {
            case FramedPacket packet:
            {
                lock (TickBuffer)
                {
                    var body = packet.Body;
                    TickBuffer.WriteBytes(body, body.ReaderIndex, body.ReadableBytes);
                    PreventiveWrite();
                }

                break;
            }
            case IByteBuffer buffer:
            {
                lock (TickBuffer)
                {
                    TickBuffer.WriteBytes(buffer, buffer.ReaderIndex, buffer.ReadableBytes);
                    PreventiveWrite();
                }

                break;
            }
            case IServerPacket packet:
            {
                var framedPacket = BufUtil.createFramedPacket(packet, true);
                lock (TickBuffer)
                {
                    TickBuffer.WriteBytes(framedPacket);
                    PreventiveWrite();
                }
                framedPacket.Release();
                break;
            }
            default:
                throw new ArgumentException("Invalid data type");
        }
    }

    private void PreventiveWrite()
    {
        if (TickBuffer.WriterIndex > FLUSH_SIZE)
        {
            WriteWaitingPackets();
        }
    }
    
    private void WriteWaitingPackets()
    {
        lock (TickBuffer)
        {
            var copy = TickBuffer.Copy();
            var writerFuture = channel.WriteAsync(new FramedPacket(copy));
            writerFuture.ContinueWith((task) =>
            {
                copy.Release();
            });
            TickBuffer.Clear();
        }
    }
    
}