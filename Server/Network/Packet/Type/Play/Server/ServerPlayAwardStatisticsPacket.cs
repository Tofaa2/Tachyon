using Server.Network.Binary;
using Server.Statistics;

namespace Server.Network.Packet.Type.Play.Server;

public record ServerPlayAwardStatisticsPacket(IDictionary<PlayerStatistic, int> Statistics) : IServerPacket
{
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.VAR_INT, Statistics.Count);
        foreach (var v in Statistics)
        {
            writer.Write(BinaryBuffer.VAR_INT, (int)v.Key.Category);
            writer.Write(BinaryBuffer.VAR_INT, v.Key.StatisticId);
            writer.Write(BinaryBuffer.VAR_INT, v.Value);
        }
    }
}