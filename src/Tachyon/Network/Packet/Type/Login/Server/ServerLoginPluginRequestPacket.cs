using DotNetty.Buffers;
using Tachyon.Namespace;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Server;

public record ServerLoginPluginRequestPacket(int MessageId, string Identifier, byte[]? Data) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.VAR_INT, MessageId);
        writer.Write(BinaryBuffer.STRING, Identifier);
        writer.Buffer.WriteBytes(Data);
    }
}