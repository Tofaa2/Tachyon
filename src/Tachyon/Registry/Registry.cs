using Tachyon.Namespace;
using Tachyon.Nbt;

namespace Tachyon.Registry;

public class Registry<T> where T : INbtSerializable
{
    
    private readonly Dictionary<NamespaceId, T> _entries = new();
    private readonly Dictionary<NamespaceId, Nbt.Nbt> _nbtEntries = new();
        
    
    public void AddEntry(NamespaceId id, T entry)
    {
        _entries.Add(id, entry);
        _nbtEntries.Add(id, entry.ToNbt());
    }

    public NbtCompound Compile()
    {
        NbtCompound c = new();
        foreach (var entry in _nbtEntries)
        {
            c.Add(entry.Key.Full, entry.Value);
        }

        return c;
    }
    
}