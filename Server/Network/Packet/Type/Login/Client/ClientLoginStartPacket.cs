using Server.Network.Binary;

namespace Server.Network.Packet.Type.Login.Client;

public class ClientLoginStartPacket : IClientPacket
{
    
    public string Username { get; private set; }
    public Guid Uuid { get; private set; }
    
    public void Read(BinaryBuffer reader)
    {
        Username = reader.Read(BinaryBuffer.STRING);
        if (Username.Length > 16)
        {
            throw new InvalidDataException("Username is too long");
        }

        Uuid = reader.Read(BinaryBuffer.UUID);
    }
}