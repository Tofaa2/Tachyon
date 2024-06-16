using DotNetty.Buffers;

namespace Tachyon.Network.Binary;

public interface IWritable
{

    void Write(IByteBuffer writer);

}