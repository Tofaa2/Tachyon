using DotNetty.Buffers;

namespace Tachyon.Nbt.Io;

public class NbtReader(IByteBuffer buffer, bool releaseBuffer = false)
{

    private readonly ReadOnlyByteBufferStream _stream = new ReadOnlyByteBufferStream(buffer, releaseBuffer);

}