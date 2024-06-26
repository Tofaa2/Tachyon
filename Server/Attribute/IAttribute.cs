using Server.Namespace;
using Server.Registry;

namespace Server.Attribute;

public interface IAttribute : IRegistriedStaticProtocolObject<Registry.Registry.AttributeEntry>
{

    public static IAttribute? FromId(NamespaceId id)
    {
        return AttributeImpl.REGISTRY.Get(id);
    }

    public static IAttribute? FromId(string id)
    {
        return AttributeImpl.REGISTRY.Get(id);
    }
    
    public static IAttribute? FromId(int protocolId)
    {
        return AttributeImpl.REGISTRY.GetId(protocolId);
    }
    
    
    public string TranslationKey => Registry.TranslationKey;
    public double DefaultValue => Registry.DefaultValue;
    
    NamespaceId IStaticProtocolObject.Id => Registry.Id;
    int IProtocolObject.ProtocolId => Registry.ProtocolId;
}