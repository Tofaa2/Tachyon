using System.Text.Json.Nodes;
using Server.Chat.Text;
using System.Reflection;
using Server.Collision;
using Server.Item.Armor;
using Server.Namespace;
using Server.Util;
using Server.World.Block;

namespace Server.Registry;

public static class Registry
{

    public interface IEntry;


    public record EnchantmentEntry(
        NamespaceId Id,
        IComponent Description,
        int AnvilCost
        ) : IEntry;
    public record StatisticTypeEntry(
        int ProtocolId,
        NamespaceId Id) : IEntry;
    public record TrimMaterialEntry(
        NamespaceId Id,
        ITrimMaterial.Description Description,
        string AssetName,
        string Ingredient,
        float ItemModelIndex
        ) : IEntry;
    public record MaterialEntry(
        NamespaceId Id,
        int ProtocolId,
        string TranslationKey,
        IBlock CorrespondingBlock,
        JsonObject DefaultComponents

        ) : IEntry;
    public record BlockEntry(
        NamespaceId Id,
        int ProtocolId,
        string TranslationKey,
        double ExplosionResistance,
        double Friction,
        short DefaultStateId,
        bool CanSpawnIn,
        double Hardness,
        PushReaction PushReaction,
        int MapColorId,
        bool Occludes,
        bool BlocksMotion,
        bool Flamable,
        bool Solid,
        bool SolidBlocking,
        AABB Shape,
        AABB CollisionShape,
        bool RedstoneConductor
        ) : IEntry;
    public record EntityEntry(
        NamespaceId Id,
        int ProtocolId,
        string TranslationKey,
        double Drag,
        double Acceleration,
        double Width,
        double Height,
        double EyeHeight
        ) : IEntry;
    public record AttributeEntry(
        string TranslationKey,
        double DefaultValue,
        NamespaceId Id,
        int ProtocolId,
        bool ClientSync,
        double MinValue,
        double MaxValue) : IEntry;


    public static Container<T> CreateStaticContainer<T, TC>(Resource resource, Func<string, JsonObject, T> loader) where TC : IEntry where T : IRegistriedStaticProtocolObject<TC>
    {
        var namespaces = new Dictionary<string, T>();

        var location = Assembly.GetExecutingAssembly().Location.Replace("/Server.dll", "") + "/Resources/" + resource.fileName;
        var entries = JsonNode.Parse(location)!.AsObject();
        var ids = new Dictionary<int, T>(entries.Count);
        foreach (var entry in entries)
        {
            var nsid = entry.Key!;
            var obj = entry.Value!.AsObject();
            var value = loader(nsid, obj);
            ids[value.ProtocolId] = value;
            namespaces[value.Id.Full] = value;
        }

        return new Container<T>(resource, namespaces, ids);
    }


    public record Resource(string fileName)
    {
        public static readonly Resource ENCHANTS = new("enchantments.json");
        public static readonly Resource ATTRIBUTES = new("attributes.json");
        public static readonly Resource BLOCKS = new("blocks.json");
        public static readonly Resource MATERIALS = new("items.json");
        public static readonly Resource TRIM_MATERIALS = new("trim_materials.json");
        public static readonly Resource STATISTIC_TYPES = new("custom_statistics.json");
    }

    public record Container<T>(Resource Resource, IDictionary<string, T> Namespaces, IDictionary<int, T> Ids) where T : IStaticProtocolObject
    {

        public T GetOrDefault(string namespaceId, T defaultValue)
        {
            return Namespaces.TryGetValue(namespaceId, out var value) ? value : defaultValue;
        }

        public T GetOrDefault(NamespaceId namespaceId, T defaultValue)
        {
            return Namespaces.TryGetValue(namespaceId.Full, out var value) ? value : defaultValue;
        }

        public T GetOrDefault(int id, T defaultValue)
        {
            return Ids[id] ?? defaultValue;
        }

        public T? Get(string namespaceId)
        {
            return Namespaces[namespaceId];
        }

        public T? Get(NamespaceId namespaceId)
        {
            return Namespaces[namespaceId.Full];
        }

        public T? GetId(int id)
        {
            return Ids[id];
        }

        public ICollection<T> ValuesCopy => Namespaces.Values.ToList();

    }


}
