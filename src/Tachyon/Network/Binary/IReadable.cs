using DotNetty.Buffers;

namespace Tachyon.Network.Binary;

public interface IReadable
{

    void Read(BinaryBuffer buffer);
    
}