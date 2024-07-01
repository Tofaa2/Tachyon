using Server.Entity;
using Server.Network.Binary;
using Server.Position;
using static Server.Network.Binary.BinaryBuffer;
namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlayJoinGamePacket(
    int EntityId, bool Hardcore,
    ICollection<string> Dimensions,
    int MaxPlayers,
    int ViewDistance,
    int SimulationDistance,
    bool ReducedDebugInfo,
    bool EnableRespawnScreen,
    bool DoLimitedCrafting,
    int DimensionType,
    string DimensionName,
    long Seed,
    Player.GameMode GameMode,
    Player.GameMode? PreviousGameMode,
    bool IsDebug,
    bool IsFlat,
    ICoordinate? DeathLocation,
    int PortalCooldown,
    bool EnforcesSecureChat
    
    ) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(INT, EntityId);
        writer.Write(BOOL, Hardcore);
        writer.WriteCollection(STRING, Dimensions);
        writer.Write(VAR_INT, MaxPlayers);
        writer.Write(VAR_INT, ViewDistance);
        writer.Write(VAR_INT, SimulationDistance);
        writer.Write(BOOL, ReducedDebugInfo);
        writer.Write(BOOL, EnableRespawnScreen);
        writer.Write(BOOL, DoLimitedCrafting);
        writer.Write(VAR_INT, DimensionType);
        writer.Write(STRING, DimensionName);
        writer.Write(LONG, Seed);
        writer.Write(BYTE, (byte) GameMode);
        if (PreviousGameMode != null)
        {
            writer.Write(BYTE, (byte)PreviousGameMode.Value);
        }
        else
        {
            writer.Buffer.WriteByte(-1);
        }
        writer.Write(BOOL, IsDebug);
        writer.Write(BOOL, IsFlat);
        writer.WriteOptional(BLOCK_POSITION, DeathLocation);
        writer.Write(VAR_INT, PortalCooldown);
        writer.Write(BOOL, EnforcesSecureChat);
    }
}