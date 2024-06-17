using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Handshake.Client;

public class ClientHandshakePacket : IClientPacket
{

    public string ServerAddress { get; private set; }
    public ushort ServerPort { get; private set; }
    public int ProtocolVersion { get; private set; }
    public Intent ConnectionIntent { get; private set; }
    
    public void Read(IByteBuffer reader)
    {
        ProtocolVersion = reader.ReadVarInt();
        ServerAddress = reader.ReadStr(255);
        ServerPort = reader.ReadUnsignedShort();
        ConnectionIntent = reader.ReadVarInt() switch
        {
            1 => Intent.Status,
            2 => Intent.Login,
            3 => Intent.Transfer,
            _ => ConnectionIntent
        };
    }

    public enum Intent
    {
        Status = 1,
        Login = 2,
        Transfer = 3
    }
}