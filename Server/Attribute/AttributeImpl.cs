using Server.Namespace;
using static Server.Registry.Registry;
namespace Server.Attribute;

internal record AttributeImpl(
    AttributeEntry Registry
) : IAttribute
{

    internal static Container<IAttribute> REGISTRY = CreateStaticContainer<IAttribute, AttributeEntry>(
        Resource.ATTRIBUTES,
        (namespaceId, jsonObject) => new AttributeImpl(
            new AttributeEntry(
                jsonObject["translationKey"].ToString(),
                jsonObject["defaultValue"].GetValue<double>(),
                NamespaceId.FromString(namespaceId),
                jsonObject["id"].GetValue<int>(),
                jsonObject["clientSync"].GetValue<bool>(),
                jsonObject["maxValue"].GetValue<double>(),
                jsonObject["minValue"].GetValue<double>()
            )));
}