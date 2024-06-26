using Server.Namespace;
using Server.Registry;

namespace Server.World.Block;

public interface IBlock : IRegistriedStaticProtocolObject<Registry.Registry.BlockEntry>
{

    public static IBlock FromId(string namespaceId)
    {
        return BlockImpl.REGISTRY.GetOrDefault(namespaceId, Blocks.AIR);
    }
    
    public static IBlock FromId(NamespaceId namespaceId)
    {
        return BlockImpl.REGISTRY.GetOrDefault(namespaceId, Blocks.AIR);
    }
    
    public static IBlock FromId(int id)
    {
        return BlockImpl.REGISTRY.GetOrDefault(id, Blocks.AIR);
    }
    
    int IProtocolObject.ProtocolId => Registry.ProtocolId;
    NamespaceId IStaticProtocolObject.Id => Registry.Id;
}