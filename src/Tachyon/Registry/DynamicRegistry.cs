using Tachyon.Namespace;
using Tachyon.Nbt;
using Tachyon.Network.Packet.Type.Configuration.Server;

namespace Tachyon.Registry;

public class DynamicRegistry<T>(NamespaceId name) where T : INbtSerializable
{
    
    private readonly Dictionary<NamespaceId, T> _entries = new();
    public readonly NamespaceId Name = name;

    public void AddEntry(NamespaceId id, T entry)
    {
        _entries.Add(id, entry);
    }

    public ServerConfigurationRegistryPacket CreateRegistryPacket()
    {
        var entries = _entries.Select(entry => new ServerConfigurationRegistryPacket.Entry(entry.Key.Full, entry.Value.ToNbt())).ToList();
        return new ServerConfigurationRegistryPacket(Name.Full, entries);
    }

}