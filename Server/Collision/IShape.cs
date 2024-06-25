using Server.Position;
using Server.World.Block;

namespace Server.Collision;

public interface IShape
{

    public ICoordinate RelativeStart { get; }
    public ICoordinate RelativeEnd { get; }

    bool IsOccluded(IShape other, BlockFace blockFace);

    bool IsFaceFull(BlockFace face)
    {
        return false;
    }
    
    bool IntersectBox(ICoordinate relative, AABB box);

    bool IntersectBoxSwept(ICoordinate rayStart, ICoordinate rayDirection, ICoordinate shapePos, AABB moving,
        SweepResult sweepResult);

}