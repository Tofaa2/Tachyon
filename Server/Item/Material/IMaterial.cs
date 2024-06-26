using Server.Namespace;
using Server.Registry;

namespace Server.Item.Material;

public interface IMaterial : IRegistriedStaticProtocolObject<Registry.Registry.MaterialEntry> 
{

    public static IMaterial FromId(string namespaceId)
    {
        return MaterialImpl.REGISTRY.GetOrDefault(namespaceId, Materials.AIR);
    }
    
    public static IMaterial FromId(NamespaceId namespaceId)
    {
        return MaterialImpl.REGISTRY.GetOrDefault(namespaceId, Materials.AIR);
    }
    
    public static IMaterial FromId(int id)
    {
        return MaterialImpl.REGISTRY.GetOrDefault(id, Materials.AIR);
    }
    
    int IProtocolObject.ProtocolId => Registry.ProtocolId;
    NamespaceId IStaticProtocolObject.Id => Registry.Id;
}