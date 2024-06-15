using DotNetty.Buffers;

namespace Tachyon.Network.Netty;

public record InboundPacket(int Id, IByteBuffer body);