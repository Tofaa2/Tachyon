using Tachyon.Network.Packet.Type.Status.Client;
using Tachyon.Network.Packet.Type.Status.Common;
using Tachyon.Network.Packet.Type.Status.Server;

namespace Tachyon.Network.Packet.Processor;

internal class Status : PacketProcessor
{
    internal Status(PlayerConnection connection) : base(connection)
    {
        
    }

    public override void Process(IPacket packet)
    {

        if (packet is ClientStatusStatusRequestPacket p)
        {
            _connection.SendPacketNow(new ServerStatusStatusResponsePacket("{\n    \"version\": {\n        \"name\": \"1.19.4\",\n        \"protocol\": 762\n    },\n    \"players\": {\n        \"max\": 100,\n        \"online\": 5,\n        \"sample\": [\n            {\n                \"name\": \"thinkofdeath\",\n                \"id\": \"4566e69f-c907-48ee-8d71-d7ba5aa00d20\"\n            }\n        ]\n    },\n    \"description\": {\n        \"text\": \"Hello, world!\"\n    },\n    \"favicon\": \"data:image/png;base64,<data>\",\n    \"enforcesSecureChat\": false,\n    \"previewsChat\": false\n}"));
        }
        else if (packet is CommonStatusPingPacket p1)
        {
            _connection.SendPacketNow(new CommonStatusPingPacket(p1.Payload));
            _connection.Disconnect();
        }
    }
}