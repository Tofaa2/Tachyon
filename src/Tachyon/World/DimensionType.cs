namespace Tachyon.World;

public record DimensionType(
    bool Ultrawarm = false,
    bool Natural = true,
    double CoordinateScale = 1.0,
    bool HasSkyLight = true,
    bool HasCeiling = false,
    float AmbientLight = 0f,
    bool PiglinSafe = false,
    long? FixedTime = null,
    bool BedWorks = true,
    bool RespawnAnchorWorks = false,
    bool HasRaids = true,
    int LogicalHeight = 319 - -64 + 1,
    int MinY = -64,
    int Height = 319 - -64 + 1,
    string InfiniBurn = "minecraft:infiniburn_overworld",
    string Effects = "minecraft:overworld"
    
)
{
    
    public static readonly int VanillaMaxHeight = 319;
    public const int VanillaMinHeight = -64;
    
    
}