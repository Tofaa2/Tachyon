using Server.Network.Packet.Type.Login.Client;
using Server.Network.Packet.Type.Login.Server;

namespace Server.Network.Packet.Registry;

internal class LoginPacketSupplier : PacketSupplier
{

    internal LoginPacketSupplier()
    {
        RegisterServer<ServerLoginDisconnectPacket>(0x00);
        RegisterServer<ServerLoginEncryptionRequest>(0x01);
        RegisterServer<ServerLoginSuccessPacket>(0x02);
        RegisterServer<ServerLoginSetCompressionPacket>(0x03);
        RegisterServer<ServerLoginPluginRequestPacket>(0x04);
        RegisterServer<ServerLoginCookieRequestPacket>(0x05);
        
        RegisterClient(0x00, () => new ClientLoginStartPacket());
        RegisterClient(0x01, () => new ClientLoginEncryptionResponsePacket());
        RegisterClient(0x02, () => new ClientLoginPluginResposePacket());
        RegisterClient(0x03, () => new ClientLoginAcknowledgedPacket());
        RegisterClient(0x04, () => new ClientLoginCookieResponsePacket());
    }
    
    
}