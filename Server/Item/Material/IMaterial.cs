using Server.Namespace;
using Server.Registry;
using Server.Util;
using Server.World.Block;
using static Server.Registry.Registry;
namespace Server.Item.Material;

public interface IMaterial : IRegistriedStaticProtocolObject<MaterialEntry>
{

    public static IMaterial FromId(string namespaceId)
    {
        return MaterialImpl.REGISTRY.GetOrDefault(namespaceId, Materials.Air);
    }
    
    public static IMaterial FromId(NamespaceId namespaceId)
    {
        return MaterialImpl.REGISTRY.GetOrDefault(namespaceId, Materials.Air);
    }
    
    public static IMaterial FromId(int id)
    {
        return MaterialImpl.REGISTRY.GetOrDefault(id, Materials.Air);
    }
    
    int IProtocolObject.ProtocolId => Registry.ProtocolId;
    NamespaceId IStaticProtocolObject.Id => Registry.Id;
}