using Server.Network.Packet.Type.Play.Server;

namespace Server.Network.Packet.Registry;

internal class PlayPacketSupplier : PacketSupplier
{

    internal PlayPacketSupplier()
    {
        RegisterServer<ServerPlayBundleDelimiterPacket>(0x00);
        RegisterServer<ServerPlaySpawnEntityPacket>(0x01);
        RegisterServer<ServerPlaySpawnExperienceOrbPacket>(0x02);
        RegisterServer<ServerPlayEntityAnimationPacket>(0x03);
        RegisterServer<ServerPlayAwardStatisticsPacket>(0x04);
        RegisterServer<ServerPlayAckgnowledgeBlockChangePacket>(0x05);
        RegisterServer<ServerPlaySetBlockDestroyStagePacket>(0x06);
        RegisterServer<ServerPlayBlockEntityDataPacket>(0x07);
        RegisterServer<ServerPlayBlockActionPacket>(0x08);
        RegisterServer<ServerPlayBlockUpdatePacket>(0x09);
        RegisterServer<ServerPlayBossBarPacket>(0x0A);
        RegisterServer<ServerPlayChangeDifficultyPacket>(0x0B);
        RegisterServer<ServerPlayChunkBatchFinishedPacket>(0x0C);
        RegisterServer<ServerPlayChunkBatchStartPacket>(0x0D);
        
        RegisterServer<ServerPlayJoinGamePacket>(0x2B);
    }
    
}