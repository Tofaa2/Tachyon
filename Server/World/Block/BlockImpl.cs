using Server.Namespace;
using static Server.Registry.Registry;
namespace Server.World.Block;

internal record BlockImpl(BlockEntry Registry) : IBlock
{

    internal static Container<IBlock> REGISTRY = CreateStaticContainer<IBlock, BlockEntry>(
        Resource.BLOCKS, 
        (namespaceId, jsonObject) => new BlockImpl(
            new BlockEntry(
                NamespaceId.FromString(namespaceId),
                jsonObject["id"].GetValue<int>(),
                jsonObject["translationKey"].ToString(),
                jsonObject["explosionResistance"].GetValue<double>(),
                jsonObject["friction"].GetValue<double>(),
                jsonObject["defaultStateId"].GetValue<short>(),
                jsonObject["canRespawnIn"].GetValue<bool>(),
                jsonObject["hardness"].GetValue<double>(),
                PushReactionAddition.FromString(jsonObject["pushReaction"].ToString()),
                jsonObject["mapColorId"].GetValue<int>(),
                jsonObject["occludes"].GetValue<bool>(),
                jsonObject["blocksMotion"].GetValue<bool>(),
                jsonObject["flamable"].GetValue<bool>(),
                jsonObject["solid"].GetValue<bool>(),
                jsonObject["solidBlocking"].GetValue<bool>(),
                null,
                null,
                jsonObject["redstoneConductor"].GetValue<bool>()
            )));
}