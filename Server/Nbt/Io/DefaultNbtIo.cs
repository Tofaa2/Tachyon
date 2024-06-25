using System.Data;
using DotNetty.Buffers;
using Server.Nbt;
using Server.Network.Binary;

namespace Tachyon.Nbt.Io;

internal class DefaultNbtIo(DefaultIdReader reader, DefaultIdWriter writer, DefaultNameReader nameReader, DefaultNameWriter nameWriter) : NbtIo<IByteBuffer, IByteBuffer>(reader, writer, nameReader, nameWriter)
{

    internal DefaultNbtIo() : this(new DefaultIdReader(), new DefaultIdWriter(), new DefaultNameReader(),
        new DefaultNameWriter())
    {
        RegisterType(
            NbtType.End,
            (limiter, buffer) =>
            {
                limiter.Increment(8);
                return NbtEnd.Instance;
            },
            (buffer, tag) =>
            {
                
            }
        );
        RegisterType(
            NbtType.Byte,
            (limiter, buffer) =>
            {
                limiter.Increment(9);
                return new NbtByte(buffer.ReadByte());
            },
            (buffer, tag) =>
            {
                buffer.WriteByte(tag.Value);
            }
        );
            
        RegisterType(
            NbtType.Short,
            (limiter, buffer) =>
            {
                limiter.Increment(10);
                return new NbtShort(buffer.ReadShort());
            },
            (buffer, tag) =>
            {
                buffer.WriteShort(tag.Value);
            }
        );
        RegisterType(
            NbtType.Int,
            (limiter, buffer) =>
            {
                limiter.Increment(12);
                return new NbtInt(buffer.ReadInt());
            },
            (buffer, tag) =>
            {
                
            }
        );
        
        RegisterType(
            NbtType.Long,
            (limiter, buffer) =>
            {
                limiter.Increment(16);
                return new NbtLong(buffer.ReadLong());
            },
            (buffer, tag) =>
            {
                buffer.WriteLong(tag.Value);
            }
        );
        
        RegisterType(
            NbtType.Float,
            (limiter, buffer) =>
            {
                limiter.Increment(12);
                return new NbtFloat(buffer.ReadFloat());
            },
            (buffer, tag) =>
            {
                buffer.WriteFloat(tag.Value);
            }
        );
        RegisterType(
            NbtType.Double,
            (limiter, buffer) =>
            {
                limiter.Increment(16);
                return new NbtDouble(buffer.ReadDouble());
            },
            (buffer, tag) =>
            {
                buffer.WriteDouble(tag.Value);
            }
        );

        RegisterType(
            NbtType.ByteArray,
            (limiter, buffer) =>
            {
                limiter.Increment(24);
                int length = buffer.ReadInt();
                if (length >= 1 << 24)
                {
                    throw new NbtException("ByteArray length is too long: " + length);
                }

                limiter.CheckReadability(length);
                limiter.Increment(length);
                
                byte[] bytes = new byte[length];
                buffer.ReadBytes(bytes);
                return new NbtByteArray(bytes);
            },
            (buffer, tag) =>
            {
                byte[] bytes = tag.Value;
                buffer.WriteInt(bytes.Length);
                buffer.WriteBytes(bytes);
            }
        );

        RegisterType(
            NbtType.String,
            (limiter, buffer) =>
            {
                limiter.Increment(36);
                string s = buffer.ReadUtf8String();
                limiter.Increment(s.Length * 2);
                return new NbtString(s);
            },
            (buffer, tag) =>
            {
                buffer.WriteUtf8String(tag.Value);
            }
        );
        RegisterType(
            NbtType.List,
            (limiter, buffer) =>
            {
                limiter.Increment(37);
                var type = ReadNbtType(limiter, buffer);
                int size = buffer.ReadInt();

                if ((type == NbtType.End) && size > 0)
                {
                    throw new NbtException("Empty list type with non-zero size");
                } 
                limiter.Increment(size * 4);
                NbtList<Server.Nbt.Nbt> list = new NbtList<Server.Nbt.Nbt>(type, size);
                for (int i = 0; i < size; i++)
                {
                    list.Add(ReadNbt(limiter, buffer, type));
                }

                return list;
            },
            (buffer, tag) =>
            {
                WriteNbtType(buffer, tag.EntryType);
                buffer.WriteInt(tag.Count);
                foreach (var nbt in tag)
                {
                    WriteNbt(buffer, nbt);
                }
            }
        );

        RegisterType(
            NbtType.String,
            (limiter, buffer) =>
            {
                limiter.Increment(48);
                NbtCompound compound = new NbtCompound();
                NbtType valueType;
                while (true)
                {
                    valueType = ReadNbtType(limiter, buffer);
                    if (valueType == NbtType.End)
                    {
                        break;
                    }

                    string name = ReadNbtName(limiter, buffer);
                    Server.Nbt.Nbt nbt = ReadNbt(limiter, buffer, valueType);
                    if (!compound.Has(name))
                    {
                        limiter.Increment(36);
                    }
                    compound.Put(name,nbt);
                }
                return compound;
            },
            (buffer, tag) =>
            {
                foreach (var (name, nbt) in tag)
                {
                    WriteNbtType(buffer, nbt.Type);
                    WriteNbtName(buffer, name);
                    WriteNbt(buffer, nbt);
                }
                WriteNbtType(buffer, NbtType.End);
            }
        );
        
        RegisterType(
            NbtType.IntArray,
            (limiter, buffer) =>
            {
                limiter.Increment(24);
                int length = buffer.ReadInt();
                if (length >= 1 << 24)
                {
                    throw new NbtException("IntArray length is too long: " + length);
                }
                limiter.CheckReadability(length);
                limiter.Increment(length);

                int[] array = new int[length];
                for (int i = 0; i < length; i++)
                {
                    array[i] = buffer.ReadInt();
                }
                return new NbtIntArray(array);
            },
            (buffer, tag) =>
            {
                int[] array = tag.Value;
                buffer.WriteInt(array.Length);
                foreach (int i in array)
                {
                    buffer.WriteInt(i);
                }
            }
        );
        RegisterType(
            NbtType.LongArray,
            (limiter, buffer) =>
            {
                limiter.Increment(24);
                int length = buffer.ReadInt();
                limiter.CheckReadability(length * 8);
                limiter.Increment(length * 8);
                
                long[] array = new long[length];
                for (int i = 0; i < length; i++)
                {
                    array[i] = buffer.ReadLong();
                }
                return new NbtLongArray(array);
            },
            (buffer, tag) =>
            {
                long[] array = tag.Value;
                buffer.WriteInt(array.Length);
                foreach (var i in array)
                {
                    buffer.WriteLong(i);
                }
            }
        );
    }


}

internal class DefaultNameReader : NbtIo<IByteBuffer, IByteBuffer>.INameReader<IByteBuffer>
{
    public string ReadName(NbtLimiter limiter, IByteBuffer from)
    {
        string utf8 = from.ReadUtf8String();
        limiter.Increment(utf8.Length * 2 + 28);
        return utf8;
    }
}

internal class DefaultNameWriter : NbtIo<IByteBuffer, IByteBuffer>.INameWriter<IByteBuffer>
{
    public void WriteName(IByteBuffer to, string name)
    {
        to.WriteUtf8String(name);
    }
}

internal class DefaultIdReader : NbtIo<IByteBuffer, IByteBuffer>.IIdReader<IByteBuffer>
{
    public int ReadId(NbtLimiter limiter, IByteBuffer from)
    {
        limiter.Increment(1);
        return from.ReadByte();
    }
}

internal class DefaultIdWriter : NbtIo<IByteBuffer, IByteBuffer>.IIdWriter<IByteBuffer>
{
    public void WriteId(IByteBuffer to, int id)
    {
        to.WriteByte((byte) id);
    }
}