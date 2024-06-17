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
        ServerAddress = reader.ReadStr(255); // 255 is the max length of a hostname except for bungeee
        ServerPort = reader.ReadUnsignedShort();

        int var = reader.ReadVarInt();
        switch (var)
        {
            case 1:
                ConnectionIntent = Intent.Status;
                break;
            case 2:
                ConnectionIntent = Intent.Login;
                break;
            case 3:
                ConnectionIntent = Intent.Transfer;
                break;
        }
    }

    public enum Intent
    {
        Status = 1,
        Login = 2,
        Transfer = 3
    }
}