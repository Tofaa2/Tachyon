using Tachyon.Network.Packet.Type.Status;
using Tachyon.Network.Packet.Type.Status.Client;
using Tachyon.Network.Packet.Type.Status.Server;

namespace Tachyon.Network.Packet.Processor;

internal class StatusPacketProcessor(Tachyon server, PlayerConnection connection) : PacketProcessor(server, connection)
{
    public override void Process(IClientPacket packet)
    {
        switch (packet)
        {
            case ClientStatusRequestPacket p:
                connection.SendPacketNow(new ServerStatusResponsePacket("{\n    \"version\": {\n        \"name\": \"1.19.4\",\n        \"protocol\": 762\n    },\n    \"players\": {\n        \"max\": 100,\n        \"online\": 5,\n        \"sample\": [\n            {\n                \"name\": \"thinkofdeath\",\n                \"id\": \"4566e69f-c907-48ee-8d71-d7ba5aa00d20\"\n            }\n        ]\n    },\n    \"description\": {\n        \"text\": \"Hello, world!\"\n    },\n    \"favicon\": \"data:image/png;base64,<data>\",\n    \"enforcesSecureChat\": false,\n    \"previewsChat\": false\n}"));
                break;
            case CommonStatusPingPacket p1:
                connection.SendPacketNow(new CommonStatusPingPacket(p1.Payload));
                connection.Disconnect();
                break;
        }
    }
}