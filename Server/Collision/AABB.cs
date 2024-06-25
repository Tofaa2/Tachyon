using Server.Position;

namespace Server.Collision;

public class AABB
{

    public readonly double Width, Height, Depth;
    public readonly ICoordinate Offset;
    public ICoordinate RelativeEnd { get; private set; }
    
    public AABB(double width, double height, double depth, ICoordinate offset)
    {
        Width = width;
        Height = height;
        Depth = depth;
        Offset = offset;
        RelativeEnd = new Point(Offset.X + Width, Offset.Y + Height, Offset.Z + Depth);
    }


}