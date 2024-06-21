using System.Collections;

namespace Tachyon.Nbt;

public class NbtCompound : Nbt, IDictionary<string, Nbt>
{
    
    private readonly Dictionary<string, Nbt> _value = new();

    
    public bool Has(string key)
    {
        return _value.ContainsKey(key);
    }
    
    public void Put(string key, Nbt value)
    {
        _value[key] = value;
    }
    
    public Nbt? Get(string key)
    {
        return _value.GetValueOrDefault(key);
    }

    public void Add(string key, Nbt value)
    {
        throw new NotImplementedException();
    }

    public bool ContainsKey(string key)
    {
        throw new NotImplementedException();
    }

    public bool Remove(string key)
    {
        return _value.Remove(key);
    }

    public bool TryGetValue(string key, out Nbt value)
    {
        throw new NotImplementedException();
    }

    public Nbt this[string key]
    {
        get => throw new NotImplementedException();
        set => throw new NotImplementedException();
    }

    public ICollection<string> Keys { get; }
    public ICollection<Nbt> Values { get; }


    public override NbtType Type => NbtType.Compound;
    public IEnumerator<KeyValuePair<string, Nbt>> GetEnumerator()
    {
        return _value.GetEnumerator();
    }

    IEnumerator IEnumerable.GetEnumerator()
    {
        return GetEnumerator();
    }

    public void Add(KeyValuePair<string, Nbt> item)
    {
        _value.Add(item.Key, item.Value);
    }

    public void Clear()
    {
        _value.Clear();
    }

    public bool Contains(KeyValuePair<string, Nbt> item)
    {
        return _value.Contains(item);
    }

    public void CopyTo(KeyValuePair<string, Nbt>[] array, int arrayIndex)
    {
        throw new NotImplementedException();
    }

    public bool Remove(KeyValuePair<string, Nbt> item)
    {
        return _value.Remove(item.Key);
    }

    public int Count => _value.Count;
    public bool IsReadOnly => false;
}