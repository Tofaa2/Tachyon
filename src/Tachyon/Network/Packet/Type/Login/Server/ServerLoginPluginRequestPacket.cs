using DotNetty.Buffers;
using Tachyon.Namespace;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Server;

public record ServerLoginPluginRequestPacket(int MessageId, NamespaceId Identifier, byte[] Data) : IServerPacket
{
    public void Write(IByteBuffer writer)
    {
        writer.WriteVarInt(MessageId);
        writer.WriteNamespace(Identifier);
        writer.WriteBytes(Data);
    }
}