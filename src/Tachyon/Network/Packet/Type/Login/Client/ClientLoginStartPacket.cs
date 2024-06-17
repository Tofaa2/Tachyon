using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Client;

public class ClientLoginStartPacket : IClientPacket
{
    
    public string Username { get; private set; }
    public Guid Uuid { get; private set; }
    
    public void Read(IByteBuffer reader)
    {
        Username = reader.ReadStr(16);
        Uuid = reader.ReadUUID();
    }
}