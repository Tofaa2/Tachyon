
namespace Tachyon.Location;


public readonly record struct Position(Point Point, float Yaw, float Pitch)
{

    public Position WithPoint(Point point)
    {
        return new Position(point, Yaw, Pitch);
    }
    public Position WithYaw(float yaw)
    {
        return new Position(Point, yaw, Pitch);
    }

    public Position WithPitch(float pitch)
    {
        return new Position(Point, Yaw, pitch);
    }

}

public readonly record struct Point(double X, double Y, double Z)
{

    public int BlockX => (int) Math.Floor(X);
    public int BlockY => (int) Math.Floor(Y);
    public int BlockZ => (int) Math.Floor(Z);

    public Point WithX(double x)
    {
        return new Point(x, Y, Z);
    }

    public Point WithY(double y)
    {
        return new Point(X, y, Z);
    }

    public Point WithZ(double z)
    {
        return new Point(X, Y, z);
    }

    public static Point operator +(Point a, Point b) => new Point(a.X + b.X, a.Y + b.Y , a.Z + b.Z);
    public static Point operator -(Point a, Point b) => new Point(a.X - b.X, a.Y - b.Y , a.Z - b.Z);
    public static Point operator *(Point a, Point b) => new Point(a.X * b.X, a.Y * b.Y, a.Z * b.Z);
    public static Point operator /(Point a, Point b) => new Point(a.X / b.X, a.Y / b.Y, a.Z / b.Z);
    public static Point operator +(Point a, double b) => new Point(a.X + b, a.Y + b, a.Z + b);
    public static Point operator -(Point a, double b) => new Point(a.X - b, a.Y - b, a.Z - b);
    public static Point operator *(Point a, double b) => new Point(a.X * b, a.Y * b, a.Z * b);
    public static Point operator /(Point a, double b) => new Point(a.X / b, a.Y / b, a.Z / b);
}
