using DotNetty.Buffers;
using Tachyon.Chat;
using Tachyon.Entity;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Configuration.Client;

public class ClientConfigurationClientInfoPacket : IClientPacket
{

    public Player.Settings Settings { get; private set; }
    
    public void Read(BinaryBuffer reader)
    {
        Settings = new();
        Settings.Locale = reader.Read(BinaryBuffer.STRING);
        if (Settings.Locale.Length > 16)
        {
            throw new Exception("Locale string too long");
        }
        Settings.ViewDistance = reader.Read(BinaryBuffer.BYTE);
        Settings.ChatMode = reader.ReadEnum<ChatMode>();
        Settings.ChatColorsEnabled = reader.Read(BinaryBuffer.BOOL);
        Settings.SkinData = reader.Read(BinaryBuffer.BYTE);
        Settings.MainHand = reader.ReadEnum<Player.Hand>();
        Settings.EnableTextFiltering = reader.Read(BinaryBuffer.BOOL);
        Settings.AllowServerListings = reader.Read(BinaryBuffer.BOOL);
    }
}