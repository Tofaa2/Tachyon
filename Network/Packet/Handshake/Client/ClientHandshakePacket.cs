using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Handshake.Client;

public class ClientHandshakePacket : IClientPacket
{

    public static readonly int MAX_SERVER_ADDRESS_LENGTH = 255; // 2500 on bungee but ohwell.
    
    public int ProtocolVersion;
    public string ServerAddress;
    public ushort ServerPort;
    public int NextState;

    public void Read(NetworkBuffer reader)
    {
        ProtocolVersion = reader.Read(NetworkBufferTypes.VAR_INT);
        ServerAddress = reader.Read(NetworkBufferTypes.STRING);
        CheckAddress();
        ServerPort = reader.Read(NetworkBufferTypes.USHORT);
        NextState = reader.Read(NetworkBufferTypes.VAR_INT);
    }

    public void Write(NetworkBuffer writer)
    {
        CheckAddress();
        writer.Write(NetworkBufferTypes.VAR_INT, ProtocolVersion);
        writer.Write(NetworkBufferTypes.STRING, ServerAddress);
        writer.Write(NetworkBufferTypes.USHORT, ServerPort);
        writer.Write(NetworkBufferTypes.VAR_INT, NextState);
    }

    private void CheckAddress()
    {
        if (ServerAddress.Length > MAX_SERVER_ADDRESS_LENGTH)
        {
            throw new Exception("Server address is too long!");
        } 
    }

    public int Id { get; }
}