using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Status.Server;

public class ServerStatusResponsePacket : IServerPacket
{

    public string JsonResponse;

    public void Read(NetworkBuffer reader)
    {
        JsonResponse = reader.Read(NetworkBufferTypes.STRING);
    }

    public void Write(NetworkBuffer writer)
    {
        writer.Write(NetworkBufferTypes.STRING, JsonResponse);
    }

    public int Id => ServerPacketIdentifier.STATUS_RESPONSE;
}