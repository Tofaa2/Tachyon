
namespace Tachyon.Utils;

public interface IObjectArray<T>
{

    public static IObjectArray<C> SingleThreaded<C>(int size)
    {
        return new SingleThreadObjectArray<C>(size);
    }

    public static IObjectArray<C> MultiThreaded<C>(int size)
    {
        return new MulthThreadObjectArray<C>(size);
    }

    T? Get(int index);

    void Set(int index, T? value);

    void Remove(int index)
    {
        Set(index, default(T));
    }

    void Trim();
    
}

internal class MulthThreadObjectArray<T> : IObjectArray<T>
{
    private volatile T[] array;
    private int max;

    internal MulthThreadObjectArray(int size)
    {
        this.array = new T[size];
    }

    public T? Get(int index)
    {
        return index < array.Length ? array[index] : default(T);
    }

    public void Set(int index, T? value)
    {
        if (index >= array.Length)
        {
            int newLength = index * 2 + 1;
            T[] newAr = new T[newLength];
            Array.Copy(array, newAr, newLength);
        }
        array[index] = value;
        this.max = Math.Max(this.max, index);
    }

    public void Trim()
    {
        this.array.CopyTo(this.array, max + 1);
    }
}
internal class SingleThreadObjectArray<T> : IObjectArray<T>
{
    private T[] array;
    private int max;

    internal SingleThreadObjectArray(int size)
    {
        this.array = new T[size];
    }

    public T? Get(int index)
    {
        return index < array.Length ? array[index] : default(T);
    }

    public void Set(int index, T? value)
    {
        if (index >= array.Length)
        {
            int newLength = index * 2 + 1;
            T[] newAr = new T[newLength];
            Array.Copy(array, newAr, newLength);
        }
        array[index] = value;
        this.max = Math.Max(this.max, index);
    }

    public void Trim()
    {
        this.array.CopyTo(this.array, max + 1);
    }
}