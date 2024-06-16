using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Handshake.Client;

public record ClientHandshakePacket(int Protocol, string Address, ushort Port, ConnectionState NextState ) : IPacket
{

    public ClientHandshakePacket(IByteBuffer reader) : this(
        
        reader.ReadVarInt(),
        reader.ReadStr(MaxAddrSize),
        reader.ReadUnsignedShort(),
        reader.ReadEnum<ConnectionState>()
        ) {}
    
    private const int MaxAddrSize = 255;
    
    public void Write(IByteBuffer writer)
    {
        writer.WriteVarInt(Protocol);
        writer.WriteStr(Address, MaxAddrSize);
        writer.WriteShort(Port);
        writer.WriteEnum<ConnectionState>(NextState);
    }
}