using System.Collections;
using System.Collections.Concurrent;

namespace Server.Namespace;

public class IdentifiableMap<T>(bool concurrent = false) : IDictionary<NamespaceId, T>
{

    private readonly IDictionary<NamespaceId, T> _map = concurrent ? new ConcurrentDictionary<NamespaceId, T>() : new Dictionary<NamespaceId, T>();
   
    public IEnumerator<KeyValuePair<NamespaceId, T>> GetEnumerator()
    {
        return _map.GetEnumerator();
    }

    IEnumerator IEnumerable.GetEnumerator()
    {
        return GetEnumerator();
    }

    public void Add(KeyValuePair<NamespaceId, T> item)
    {
        _map.Add(item);
    }

    public void Clear()
    {
        _map.Clear();
    }

    public bool Contains(KeyValuePair<NamespaceId, T> item)
    {
        return _map.Contains(item);
    }

    public void CopyTo(KeyValuePair<NamespaceId, T>[] array, int arrayIndex)
    {
        _map.CopyTo(array, arrayIndex);
    }

    public bool Remove(KeyValuePair<NamespaceId, T> item)
    {
        return _map.Remove(item);
    }

    public int Count => _map.Count;
    public bool IsReadOnly => _map.IsReadOnly;
    public void Add(NamespaceId key, T value)
    {
        _map.Add(key, value);
    }

    public bool ContainsKey(NamespaceId key)
    {
        return _map.ContainsKey(key);
    }

    public bool Remove(NamespaceId key)
    {
        return _map.Remove(key);
    }

    public bool TryGetValue(NamespaceId key, out T value)
    {
        return _map.TryGetValue(key, out value);
    }

    public T this[NamespaceId key]
    {
        get => _map[key];
        set => _map[key] = value;
    }

    public ICollection<NamespaceId> Keys => _map.Keys;
    public ICollection<T> Values => _map.Values;
}