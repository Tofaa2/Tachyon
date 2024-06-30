using Server.Network.Binary;
using Server.Util;

namespace Server.Network.Packet.Type.Configuration.Server;

public class ServerConfigurationCustomReportDetailsPacket(ICollection<ServerConfigurationCustomReportDetailsPacket.Report> Reports) : IServerPacket
{

    public record Report(string Title, string Description);
    
    public void Write(BinaryBuffer writer)
    {
        writer.Write(BinaryBuffer.VAR_INT, Reports.Count);
        foreach ( var report in Reports)
        {
            if (report.Title.Length > 128 || report.Description.Length > 4096)
            {
                throw new InvalidDataException("Report title (max 128 characters) or description (max 4096 characters) is too long");
            }
            writer.Write(BinaryBuffer.STRING, report.Title);
            writer.Write(BinaryBuffer.STRING, report.Description);
        }
    }
}