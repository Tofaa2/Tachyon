namespace Server.Position;

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

    public ICoordinate Add(ICoordinate other)
    {
        return new Location(Point.Add(other) as Point, Yaw, Pitch);
    }

    public ICoordinate Add(double x, double y, double z)
    {
        return new Location(Point.Add(x, y, z) as Point, Yaw, Pitch);
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