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
    public void Write(IByteBuffer writer)
    {
        writer.WriteStr(ServerId);
        
        // writer.WriteVarInt(PublicKeyLength);
        // writer.WriteBytes(PublicKey);
        writer.WriteByteArr(PublicKeyLength, PublicKey);
        
        // writer.WriteVarInt(VerifyTokenLength);
        // writer.WriteBytes(VerifyToken);
        writer.WriteByteArr(VerifyTokenLength, VerifyToken);
        
        writer.WriteBoolean(ShouldAuthenticate);
    }
}