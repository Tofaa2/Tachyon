using Server.Network.Binary;

namespace Server.World.Chunk;

public interface ISection : IWritable
{

    public IChunk Source { get; }

    int Index { get; }

    void Clear();

}
