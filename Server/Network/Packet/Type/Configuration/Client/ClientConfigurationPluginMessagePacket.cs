using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Client;

public class ClientConfigurationPluginMessagePacket : IClientPacket
{

    public string Identifier { get; private set; }
    public byte[] Data { get; private set; }
    
    public void Read(BinaryBuffer buffer)
    {
        Identifier = buffer.Read(BinaryBuffer.STRING);
        Data = buffer.Buffer.ReadBytes(buffer.Buffer.ReadableBytes).Array;
    }
}