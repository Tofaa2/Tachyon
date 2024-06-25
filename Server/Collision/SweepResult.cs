namespace Server.Collision;

public sealed class SweepResult
{

    internal double res;
    internal double normalX, normalY, normalZ;
    internal double collidedPositionX, collidedPositionY, collidedPositionZ;
    internal IShape collidedShape;

    public SweepResult(double res, double normalX, double normalY, double normalZ, double collidedPositionX, double collidedPositionY, double collidedPositionZ, IShape collidedShape)
    {
        this.res = res;
        this.normalX = normalX;
        this.normalY = normalY;
        this.normalZ = normalZ;
        this.collidedPositionX = collidedPositionX;
        this.collidedPositionY = collidedPositionY;
        this.collidedPositionZ = collidedPositionZ;
        this.collidedShape = collidedShape;
    }
}