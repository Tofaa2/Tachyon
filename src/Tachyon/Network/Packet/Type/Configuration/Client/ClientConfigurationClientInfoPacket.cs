using DotNetty.Buffers;
using Tachyon.Chat;
using Tachyon.Entity;
using Tachyon.Network.Binary;

namespace Tachyon.Network.Packet.Type.Configuration.Client;

public class ClientConfigurationClientInfoPacket : IClientPacket
{

    public Player.Settings Settings { get; private set; }
    
    public void Read(IByteBuffer reader)
    {
        Settings = new();
        Settings.Locale = reader.ReadStr(16);
        Settings.ViewDistance = reader.ReadByte();
        Settings.ChatMode = reader.ReadEnum<ChatMode>();
        Settings.ChatColorsEnabled = reader.ReadBoolean();
        Settings.SkinData = reader.ReadByte();
        Settings.MainHand = reader.ReadEnum<Player.Hand>();
        Settings.EnableTextFiltering = reader.ReadBoolean();
        Settings.AllowServerListings = reader.ReadBoolean();
    }
}