using System.Collections.Concurrent;
using Server.Gamedata;
using Server.Namespace;
using Server.Network.Packet;

namespace Server.Registry;

public class DynamicRegistry<T>
{

    private readonly IServerPacket vanillaRegistryCachedPacket;
    
    private object lockObject = new();
    private ConcurrentDictionary<int, T> entriesById = new();
    private ConcurrentDictionary<string, T> entriesByName = new();
    private ConcurrentDictionary<int, NamespaceId> idByName = new();
    private ConcurrentDictionary<int, DataPack> packById = new();

    private readonly string id;

    public record Key<T>(NamespaceId Namespace)
    {
        public string Name => Namespace.Full;
    }
    
}