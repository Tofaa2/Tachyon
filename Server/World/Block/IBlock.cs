using Server.Namespace;
using Server.Registry;
using Server.Util;
using static Server.Registry.Registry;


namespace Server.World.Block;

public interface IBlock : IRegistriedStaticProtocolObject<BlockEntry>
{

    public static IBlock FromId(string namespaceId)
    {
        return BlockImpl.REGISTRY.GetOrDefault(namespaceId, Blocks.Air);
    }
    
    public static IBlock FromId(NamespaceId namespaceId)
    {
        return BlockImpl.REGISTRY.GetOrDefault(namespaceId, Blocks.Air);
    }
    
    public static IBlock FromId(int id)
    {
        return BlockImpl.REGISTRY.GetOrDefault(id, Blocks.Air);
    }
    
    int IProtocolObject.ProtocolId => Registry.ProtocolId;
    NamespaceId IStaticProtocolObject.Id => Registry.Id;
}