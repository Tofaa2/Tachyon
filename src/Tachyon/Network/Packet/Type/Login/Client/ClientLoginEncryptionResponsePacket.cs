using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Client;

public class ClientLoginEncryptionResponsePacket : IClientPacket
{

    public int SharedSecretLength;
    public byte[] SharedSecret;
    public int VerifyTokenLength;
    public byte[] VerifyToken;
    
    public void Read(IByteBuffer reader)
    {
        SharedSecret = reader.ReadByteArr();
        SharedSecretLength = SharedSecret.Length;
        VerifyToken = reader.ReadByteArr();
        VerifyTokenLength = VerifyToken.Length;
    }
}