using Server.Network.Binary;

namespace Server.Network.Packet.Type.Handshake.Client;

public class ClientHandshakePacket : IClientPacket
{

    public string ServerAddress { get; private set; }
    public ushort ServerPort { get; private set; }
    public int ProtocolVersion { get; private set; }
    public Intent ConnectionIntent { get; private set; }
    
    public void Read(BinaryBuffer reader)
    {
        ProtocolVersion = reader.Read(BinaryBuffer.VAR_INT);
        ServerAddress = reader.Read(BinaryBuffer.STRING);
        if (ServerAddress.Length > 255)
        {
            throw new ArgumentOutOfRangeException("ServerAddress", "Server address is too long");
        }

        ServerPort = reader.Read(BinaryBuffer.USHORT);
        ConnectionIntent = reader.ReadEnum<Intent>();
    }

    public enum Intent
    {
        Status = 1,
        Login = 2,
        Transfer = 3
    }
}