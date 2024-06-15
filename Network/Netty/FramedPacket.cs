using DotNetty.Buffers;

namespace Tachyon.Network.Netty;

public record FramedPacket(IByteBuffer Body);