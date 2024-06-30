using Server.Namespace;
using static Server.Registry.Registry;

namespace Server.Item.Armor;

internal record TrimMaterialImpl(TrimMaterialEntry Registry) : ITrimMaterial
{

    public static Container<ITrimMaterial> REGISTRY = CreateStaticContainer<ITrimMaterial, TrimMaterialEntry>(
        
        Resource.TRIM_MATERIALS,
        (namespaceId, json) =>
        {
            return new TrimMaterialImpl(new TrimMaterialEntry(
                NamespaceId.FromString(namespaceId),
                new ITrimMaterial.Description(
                    json["description"].AsObject()["color"].GetValue<string>(),
                    json["description"].AsObject()["translate"].GetValue<string>()
                ),
                json["asset_name"].GetValue<string>(),
                json["ingredient"].GetValue<string>(),
                json["item_model_index"].GetValue<float>()
            ));
        }
    );
    
    public int ProtocolId => -1; // Idk how to do this better
}