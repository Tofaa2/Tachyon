using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Client;

public class ClientLoginEncryptionResponsePacket : IClientPacket
{

    public int SharedSecretLength;
    public byte[] SharedSecret;
    public int VerifyTokenLength;
    public byte[] VerifyToken;
    
    public void Read(BinaryBuffer reader)
    {
        SharedSecret = reader.Read(BinaryBuffer.BYTE_ARRAY);
        SharedSecretLength = SharedSecret.Length;
        VerifyToken = reader.Read(BinaryBuffer.BYTE_ARRAY);
        VerifyTokenLength = VerifyToken.Length;
    }
}