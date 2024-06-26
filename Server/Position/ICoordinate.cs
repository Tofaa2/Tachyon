namespace Server.Position;

public interface ICoordinate
{
    
    public double X { get; }
    public double Y { get; }
    public double Z { get; }
    
    public int BlockX => (int) Math.Floor(X);
    public int BlockY => (int) Math.Floor(Y);
    public int BlockZ => (int) Math.Floor(Z);
    
    public double DistanceSquared(ICoordinate other);
    public double Distance(ICoordinate other);
    
    public ICoordinate Add(ICoordinate other);
    
    public ICoordinate Add(double x, double y, double z);
    
}