namespace Server.Position;

public class Direction
{

    public static readonly Direction DOWN = new(0, -1, 0);
    public static readonly Direction UP = new(0, 1, 0);
    public static readonly Direction NORTH = new(0, 0, -1);
    public static readonly Direction SOUTH = new(0, 0, 1);
    public static readonly Direction WEST = new(-1, 0, 0);
    public static readonly Direction EAST = new(1, 0, 0);

    public readonly int NormalX, NormalY, NormalZ;
    
    private Direction(int normalX, int normalY, int normalZ)
    {
        NormalX = normalX;
        NormalY = normalY;
        NormalZ = normalZ;
    }

    public Direction Opposite()
    {
        if (this == DOWN) return UP;
        if (this == UP) return DOWN;
        if (this == NORTH) return SOUTH;
        if (this == SOUTH) return NORTH;
        if (this == WEST) return EAST;
        if (this == EAST) return WEST;
        throw new ArgumentOutOfRangeException();
    }

}