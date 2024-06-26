using System.Text.Json.Nodes;
using Server.Collision;
using Server.Namespace;
using Server.Util;
using Server.World.Block;

namespace Server.Registry;

public static class Registry
{

    public interface IEntry;


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
    

    public static Container<T> CreateStaticContainer<T, TC>(Resource resource, Func<string, JsonObject, T> loader)where TC : IEntry where T : IRegistriedStaticProtocolObject<TC> 
    {
        var namespaces = new Dictionary<string, T>();
        var entries = JsonNode.Parse(File.ReadAllText(resource.fileName))!.AsObject();
        var ids = IObjectArray<T>.SingleThreaded<T>(entries.Count);
        foreach (var entry in entries)
        {
            var nsid = entry.Key!;
            var obj = entry.Value!.AsObject();
            var value = loader(nsid, obj);
            ids.Set(value.ProtocolId, value);
            namespaces[value.Id.Full] = value;
        }

        return new Container<T>(resource, namespaces, ids);
    }

    
    public record Resource(string fileName)
    {
        public static readonly Resource ATTRIBUTES = new("attributes.json");
        public static readonly Resource BLOCKS = new("blocks.json");
    }

    public record Container<T>(Resource Resource, IDictionary<string, T> Namespaces, IObjectArray<T> Ids) where T : IStaticProtocolObject
    {

        public T Get(string namespaceId)
        {
            return Namespaces[namespaceId];
        }
        
        public T Get(NamespaceId namespaceId)
        {
            return Namespaces[namespaceId.Full];
        }

        public T getId(int id)
        {
            return Ids.Get(id);
        }
        
        public ICollection<T> ValuesCopy => Namespaces.Values.ToList();
        
    }
    

}