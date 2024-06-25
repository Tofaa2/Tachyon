using Server.Position;

namespace Server.World.Block;

public class BlockFace
{

    public static readonly BlockFace BOTTOM = new(Direction.DOWN);
    public static readonly BlockFace TOP = new(Direction.UP);
    public static readonly BlockFace NORTH = new(Direction.NORTH);
    public static readonly BlockFace SOUTH = new(Direction.SOUTH);
    public static readonly BlockFace WEST = new(Direction.WEST);
    public static readonly BlockFace EAST = new(Direction.EAST);

    public readonly Direction Direction;
    
    private BlockFace(Direction direction)
    {
        Direction = direction;
    }

}