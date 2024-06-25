using DotNetty.Buffers;
using Server.Nbt;

namespace Tachyon.Nbt.Io;

public class NbtIo<TIn, TOut>(
    NbtIo<TIn, TOut>.IIdReader<TIn> idReader,
    NbtIo<TIn, TOut>.IIdWriter<TOut> idWriter,
    NbtIo<TIn, TOut>.INameReader<TIn> nameReader,
    NbtIo<TIn, TOut>.INameWriter<TOut> nameWriter)
{

    protected readonly IIdReader<TIn> IdReader = idReader;
    protected readonly IIdWriter<TOut> IdWriter = idWriter;
    
    protected readonly INameReader<TIn> NameReader = nameReader;
    protected readonly INameWriter<TOut> NameWriter = nameWriter;
    
    protected readonly Dictionary<NbtType, Func<NbtLimiter, TIn, Server.Nbt.Nbt>> TagReaders = new();
    protected readonly Dictionary<NbtType, Action<TOut, Server.Nbt.Nbt>> TagWriters = new();

    
    public void RegisterType<T>(NbtType type, Func<NbtLimiter, TIn, T> reader, Action<TOut, T> writer) where T : Server.Nbt.Nbt
    {
        TagReaders[type] = reader;
        TagWriters[type] = (to, tag) => writer(to, (T) tag);
    }

    public Server.Nbt.Nbt? DeserializeNbt(NbtLimiter limiter, TIn from, bool named = true)
    {
        var type = ReadNbtType(limiter, from);
        if (type == NbtType.End)
        {
            return null;
        }

        if (named)
        {
            ReadNbtName(limiter, from);
        }

        return ReadNbt(limiter, from, type);
    }

    public void SerializeNbt(TOut to, Server.Nbt.Nbt tag, bool named = true)
    {
        var type = tag.Type;
        WriteNbtType(to, type);
        if (type == NbtType.End)
        {
            return;
        }

        if (named)
        {
            WriteNbtName(to, "");
        }

        WriteNbt(to, tag);
    }

    public void WriteNbt(TOut stream, Server.Nbt.Nbt tag)
    {
        if (!TagWriters.TryGetValue(tag.Type, out var writer))
        {
            throw new InvalidOperationException($"No writer for tag type {tag.Type}");
        }
        writer(stream, tag);
    }

    public Server.Nbt.Nbt ReadNbt(NbtLimiter limiter, TIn stream, NbtType type)
    {
        if (!TagReaders.TryGetValue(type, out var reader))
        {
            throw new InvalidOperationException($"No reader for tag type {type}");
        }
        return reader(limiter, stream);
    }
    
    public NbtType ReadNbtType(NbtLimiter limiter, TIn from)
    {
        int id = IdReader.ReadId(limiter, from);
        return (NbtType) id;
    }

    public void WriteNbtType(TOut stream, NbtType type)
    {
        IdWriter.WriteId(stream, (int) type);
    }
    
    public string ReadNbtName(NbtLimiter limiter, TIn from)
    {
        return NameReader.ReadName(limiter, from);
    }
    
    public void WriteNbtName(TOut to, string name)
    {
        NameWriter.WriteName(to, name);
    }

    public interface IIdWriter<T>
    {
        public void WriteId(T to, int id);
    }

    public interface IIdReader<T>
    {
        public int ReadId(NbtLimiter limiter, T from);
    }

    public interface INameWriter<T>
    {
        public void WriteName(T to, string name);
    }
    
    public interface INameReader<T>
    {
        public string ReadName(NbtLimiter limiter, T from);
    }
    
}

