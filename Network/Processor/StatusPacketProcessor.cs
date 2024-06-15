using Tachyon.Network.Packet;
using Tachyon.Network.Packet.Status.Client;
using Tachyon.Network.Packet.Status.Server;
using Tachyon.Ping;

namespace Tachyon.Network.Processor;

internal class StatusPacketProcessor(TachyonServer _server, PlayerConnection _connection) : PacketProcessor(_server, _connection)
{
    public override void Handle(IClientPacket packet)
    {
        if (packet is ClientStatusRequestPacket statusRequestPacket)
        {
            ServerListPingData data = new ServerListPingData();
            ServerStatusResponsePacket response = new ServerStatusResponsePacket();
            string s= """
                      {
                          "version": {
                              "name": "1.19.4",
                              "protocol": 762
                          },
                          "players": {
                              "max": 100,
                              "online": 5,
                              "sample": [
                                  {
                                      "name": "thinkofdeath",
                                      "id": "4566e69f-c907-48ee-8d71-d7ba5aa00d20"
                                  }
                              ]
                          },
                          "description": {
                              "text": "Hello, world!"
                          },
                          "favicon": "data:image/png;base64,<data>",
                          "enforcesSecureChat": false,
                          "previewsChat": false
                      }
                      """;
            response.JsonResponse = s;
            Console.WriteLine("HELLO");
            _connection.SendPacket(response);
        }
        else if (packet is ClientStatusPingRequestPacket pingRequestPacket)
        {
            _connection.SendPacket(new ServerStatusPingResponsePacket()
            {
                Payload = pingRequestPacket.Payload
            });
            _connection.Disconnect();
        }
    }
}