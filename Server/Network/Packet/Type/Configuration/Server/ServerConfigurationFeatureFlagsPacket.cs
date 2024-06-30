using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public class ServerConfigurationFeatureFlagsPacket(string[] Features) : IServerPacket
{

    public const string FEATURE_VANILLA = "minecraft:vanilla";
    public const string FEATURE_BUNDLE = "minecraft:bundle";
    public const string FEATURE_TRADE_REBALANCE = "minecraft:trade_rebalance";
    
    public void Write(BinaryBuffer writer)
    {
        writer.WriteCollection(BinaryBuffer.STRING, Features);
    }
}