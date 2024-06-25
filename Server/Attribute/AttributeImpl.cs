using Server.Namespace;
namespace Server.Attribute;

internal record AttributeImpl(
    Registry.Registry.AttributeEntry Registry
) : IAttribute
{

    internal static readonly Registry.Registry.Container<IAttribute> REGISTRY =
        global::Server.Registry.Registry.CreateStaticContainer(
            global::Server.Registry.Registry.Resource.ATTRIBUTES,
            (namespaceId, jsonObject) =>(IAttribute) new AttributeImpl(
                new Registry.Registry.AttributeEntry(
                    jsonObject["translationKey"].ToString(),
                    jsonObject["defaultValue"].GetValue<double>(),
                    NamespaceId.FromString(namespaceId),
                    jsonObject["id"].GetValue<int>(),
                    jsonObject["clientSync"].GetValue<bool>(),
                    jsonObject["maxValue"].GetValue<double>(),
                    jsonObject["minValue"].GetValue<double>()
                )));
}