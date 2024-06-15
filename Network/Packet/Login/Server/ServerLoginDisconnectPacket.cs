using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Login.Server;

public class ServerLoginDisconnectPacket : IServerPacket
{

    public string JsonReason;


    public void Read(NetworkBuffer reader)
    {
        JsonReason = reader.Read(NetworkBufferTypes.STRING);
    }

    public void Write(NetworkBuffer writer)
    {
        writer.Write(NetworkBufferTypes.STRING, JsonReason);
    }

    public int Id => ServerPacketIdentifier.LOGIN_DISCONNECT;
}