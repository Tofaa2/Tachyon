using Server.Position;
using Server.World.Block;

namespace Server.Collision;

public sealed class AABB : IShape
{

    public readonly double Width, Height, Depth;
    public readonly ICoordinate Offset;
    private ICoordinate? _relativeEnd;

    public ICoordinate RelativeEnd
    {
        get
        {
            if (_relativeEnd == null)
            {
                _relativeEnd = Offset.Add(Width, Height, Depth);
            }
            return _relativeEnd!;
        }
    }
    
    public double MinX => RelativeStart.X;
    public double MinY => RelativeStart.Y;
    public double MinZ => RelativeStart.Z;
    public double MaxX => RelativeEnd.X;
    public double MaxY => RelativeEnd.Y;
    public double MaxZ => RelativeEnd.Z;

    public bool IsOccluded(IShape other, BlockFace blockFace)
    {
        return false;
    }

    public bool IntersectBox(ICoordinate positionRelative, AABB boundingBox)
    {
        return (MinX + positionRelative.X <= boundingBox.MaxX - Point.Epsilon / 2 && MaxX + positionRelative.X >= boundingBox.MinX + Point.Epsilon / 2) &&
               (MinY + positionRelative.Y <= boundingBox.MaxY - Point.Epsilon / 2 && MaxY + positionRelative.Y >= boundingBox.MinY + Point.Epsilon / 2) &&
               (MinZ + positionRelative.Z <= boundingBox.MaxZ - Point.Epsilon / 2 && MaxZ + positionRelative.Z >= boundingBox.MinZ + Point.Epsilon / 2);
    }

    public bool IntersectBoxSwept(ICoordinate rayStart, ICoordinate rayDirection, ICoordinate shapePos, AABB moving,
        SweepResult sweepResult)
    {
        throw new NotImplementedException();
    }

    public AABB(double width, double height, double depth, ICoordinate offset)
    {
        Width = width;
        Height = height;
        Depth = depth;
        Offset = offset;
        _relativeEnd = new Point(Offset.X + Width, Offset.Y + Height, Offset.Z + Depth);
    }

    public ICoordinate RelativeStart => Offset;
}