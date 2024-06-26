using Server.Namespace;
using Server.World.Block;
using static Server.Registry.Registry;

namespace Server.Item.Material;

internal record MaterialImpl(MaterialEntry Registry) : IMaterial
{

    internal static Container<IMaterial> REGISTRY = CreateStaticContainer<IMaterial, MaterialEntry>(
        Resource.MATERIALS,
        (namespaceId, json) => new MaterialImpl(new MaterialEntry(
            NamespaceId.FromString(namespaceId),
            json["id"].GetValue<int>(),
            json["translationKey"].GetValue<string>(),
            IBlock.FromId(json["correspondingBlock"].GetValue<string>() ?? ""),
            json["components"].AsObject()
        )));

}