namespace Tachyon.Position;

public record Location(Point Point, float Yaw, float Pitch) : ICoordinate
{
    public double X => Point.X;
    public double Y => Point.Y;
    public double Z => Point.Z;
    public double DistanceSquared(ICoordinate other)
    {
        return Point.DistanceSquared(other);
    }

    public double Distance(ICoordinate other)
    {
        return Point.Distance(other);
    }
    
    // Overload operators
    public static Location operator +(Location a, Location b)
    {
        return new Location(a.Point + b.Point, a.Yaw + b.Yaw, a.Pitch + b.Pitch);
    }
    
    public static Location operator -(Location a, Location b)
    {
        return new Location(a.Point - b.Point, a.Yaw - b.Yaw, a.Pitch - b.Pitch);
    }
    
    public static Location operator *(Location a, float b)
    {
        return new Location(a.Point * b, a.Yaw * b, a.Pitch * b);
    }
    
    public static Location operator /(Location a, float b)
    {
        return new Location(a.Point / b, a.Yaw / b, a.Pitch / b);
    }
    
    
}