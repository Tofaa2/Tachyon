using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Server;

public record ServerLoginEncryptionRequest(
    string ServerId,
    int PublicKeyLength,
    byte[] PublicKey,
    int VerifyTokenLength,
    byte[] VerifyToken,
    bool ShouldAuthenticate
) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.STRING, ServerId);
        
        // writer.WriteVarInt(PublicKeyLength);
        // writer.WriteBytes(PublicKey);
        writer.Buffer.WriteByteArr(PublicKeyLength, PublicKey);
        
        // writer.WriteVarInt(VerifyTokenLength);
        // writer.WriteBytes(VerifyToken);
        writer.Buffer.WriteByteArr(VerifyTokenLength, VerifyToken);
        
        writer.Write(BinaryBuffer.BOOL, ShouldAuthenticate);
    }
}