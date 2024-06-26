namespace Server.Position;

public record Point(double X, double Y, double Z) : ICoordinate
{
    
    public static Point Zero => new(0, 0, 0);
    public static double Epsilon = 0.000001;
    
    
    public Point WithX(double x) => new(x, Y, Z);
    public Point WithY(double y) => new(X, y, Z);
    public Point WithZ(double z) => new(X, Y, z);
    
    public int BlockX => (int) Math.Floor(X);
    public int BlockY => (int) Math.Floor(Y);
    public int BlockZ => (int) Math.Floor(Z);

    public ICoordinate Add(ICoordinate other)
    {
        return new Point(X + other.X, Y + other.Y, Z + other.Z);
    }

    public ICoordinate Add(double x, double y, double z)
    {
        return new Point(X + x, Y + y, Z + z);
    }

    public double DistanceSquared(ICoordinate other) => Math.Pow(X - other.X, 2) + Math.Pow(Y - other.Y, 2) + Math.Pow(Z - other.Z, 2);
    public double Distance(ICoordinate other) => Math.Sqrt(DistanceSquared(other));
    
    // Overload operators
    public static Point operator +(Point a, Point b) => new(a.X + b.X, a.Y + b.Y, a.Z + b.Z);
    public static Point operator -(Point a, Point b) => new(a.X - b.X, a.Y - b.Y, a.Z - b.Z);
    public static Point operator *(Point a, Point b) => new(a.X * b.X, a.Y * b.Y, a.Z * b.Z);
    public static Point operator /(Point a, Point b) => new(a.X / b.X, a.Y / b.Y, a.Z / b.Z);
    public static Point operator /(Point a, double b) => new(a.X / b, a.Y / b, a.Z / b);
    public static Point operator *(Point a, double b) => new(a.X * b, a.Y * b, a.Z * b);
    
}