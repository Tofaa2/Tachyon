using Server.Namespace;
using Server.Registry;

namespace Server.Attribute;

public interface IAttribute : IRegistriedStaticProtocolObject<Registry.Registry.AttributeEntry>
{
    public string TranslationKey => Registry.TranslationKey;
    public double DefaultValue => Registry.DefaultValue;

    NamespaceId IStaticProtocolObject.Id => Registry.Id;
    int IProtocolObject.ProtocolId => Registry.ProtocolId;
}