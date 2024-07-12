using System.Collections.ObjectModel;
using System.ComponentModel.DataAnnotations;
using Server.World.Block;

namespace Server.World.Chunk;

public interface IChunk
{

  public const int ChunkSizeX = 16;
  public const int ChunkSizeZ = 16;
  public const int ChunkSectionSize = 16;
  public const int ChunkSizeBits = 4;

  public World World { get; }

  public int ChunkX { get; }
  public int ChunkZ { get; }

  public int MinSection { get; }
  public int MaxSection { get; }

  public bool IsLoaded { get; }

  public IBlock GetBlock([Range(0, 15)] int x, [Range(0, 15)] int y, [Range(0, 15)] int z);

  public void SetBlock([Range(0, 15)] int x, [Range(0, 15)] int y, [Range(0, 15)] int z, IBlock block);

  public ReadOnlyCollection<ISection> Sections { get; }

  public ISection GetSection(int index);

  public ISection GetSectionAt(int blockY)
  {
    return GetSection(blockY >> ChunkSizeBits);
  }

}
