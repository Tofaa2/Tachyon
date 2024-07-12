using Server.Nbt;

namespace Server.World.Content;

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
    
) : INbtSerializable
{

    public NbtCompound ToNbt()
    {
        NbtCompound compound = new();
        compound.Add("fixed_time", FixedTime == null ? null : new NbtLong(FixedTime.Value));
        compound.Add("has_skylight", new NbtByte(HasSkyLight));
        compound.Add("has_ceiling", new NbtByte(HasCeiling));
        compound.Add("ultrawarm", new NbtByte(Ultrawarm));
        compound.Add("natural", new NbtByte(Natural));
        compound.Add("coordinate_scale", new NbtDouble(CoordinateScale));
        compound.Add("bed_works", new NbtByte(BedWorks));
        compound.Add("respawn_anchor_works", new NbtByte(RespawnAnchorWorks));
        compound.Add("min_y", new NbtInt(MinY));
        compound.Add("height", new NbtInt(Height));
        compound.Add("logical_height", new NbtInt(LogicalHeight));
        compound.Add("infiniburn", new NbtString(InfiniBurn));
        compound.Add("effects", new NbtString(Effects));
        compound.Add("ambient_light", new NbtFloat(AmbientLight));
        compound.Add("piglin_safe", new NbtByte(PiglinSafe));
        compound.Add("has_raids", new NbtByte(HasRaids));
        return compound;
    }
    
    public static readonly int VanillaMaxHeight = 319;
    public const int VanillaMinHeight = -64;
    
    public static readonly DimensionType Overworld = new();
    
    
}