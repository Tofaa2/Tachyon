using System.Collections;

namespace Server.Nbt;

public class NbtList<TType>(NbtType entryType, ICollection<TType> collection)
    : Nbt, ICollection<TType> where TType : Nbt
{

    
    public static NbtList<Nbt> Untyped(NbtType entryType, ICollection<Nbt> collection)
    {
        return new NbtList<Nbt>(entryType, collection);
    }
    
    public List<TType> Value => new(collection);
    public NbtType EntryType => entryType;
    
    public NbtList(NbtType entryType, int size) : this(entryType, new List<TType>(size))
    {}
    
    
    public override NbtType Type => NbtType.List;
    public IEnumerator<TType> GetEnumerator()
    {
        return Value.GetEnumerator();
    }

    IEnumerator IEnumerable.GetEnumerator()
    {
        return GetEnumerator();
    }

    public void Add(TType item)
    {
        Value.Add(item);
    }

    public void Clear()
    {
        Value.Clear();
    }

    public bool Contains(TType item)
    {
        return Value.Contains(item);
    }

    public void CopyTo(TType[] array, int arrayIndex)
    {
        Value.CopyTo(array, arrayIndex);
    }

    public bool Remove(TType item)
    {
        return Value.Remove(item);
    }

    public int Count => Value.Count;
    public bool IsReadOnly => throw new NotImplementedException();
}