using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet;

// superinterface for ANY packet, even ones that arent server/client directly.
public interface IGenericPacket;

public interface IPacket : IWritable, IGenericPacket;

public record FramedPacket(IByteBuffer Body) : IGenericPacket;
