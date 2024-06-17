using System.Reflection.Metadata;
using DotNetty.Buffers;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Login.Server;

public record ServerLoginSuccessPacket(
    Guid Uuid,
    string Username,
    int PropertiesLength,
    ServerLoginSuccessPacket.Property[] Properties,
    bool StrictErrorHandling
) : IServerPacket
{

    public record Property(string Name, string Value, bool IsSigned, string? Signature);

    public void Write(IByteBuffer writer)
    {
        writer.WriteUUID(Uuid);
        writer.WriteStr(Username);
        
        writer.WriteArray(PropertiesLength, Properties, (writer, property) =>
        {
            writer.WriteStr(property.Name);
            writer.WriteStr(property.Value);
            writer.WriteBoolean(property.IsSigned);
            writer.WriteOptional(property.Signature, (w,  p) => w.WriteStr(p));
        });
    }
}