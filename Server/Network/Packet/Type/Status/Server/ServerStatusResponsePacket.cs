using Server.Network.Binary;
using Server.Ping;

namespace Server.Network.Packet.Type.Status.Server;

public record ServerStatusResponsePacket(string JsonPayload) : IServerPacket
{

    public ServerStatusResponsePacket(ServerListPingResponse response) : this(response.ToString()) {}
    
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.STRING,JsonPayload);
    }
}