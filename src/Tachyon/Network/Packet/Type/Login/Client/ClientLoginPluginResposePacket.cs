using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Client;

public class ClientLoginPluginResposePacket : IClientPacket
{

    public int MessageId;
    public bool Successful;
    public byte[]? Data;
    
    public void Read(IByteBuffer reader)
    {
        MessageId = reader.ReadVarInt();
        Successful = reader.ReadBoolean();
        if (Successful)
        {
            int limit = reader.Capacity;
            int length = limit - reader.ReaderIndex;
            if (length <= 0)
            {
                throw new Exception("No data in packet");
            }
            Data = reader.ReadBytes(length).Array;
        }
    }
}