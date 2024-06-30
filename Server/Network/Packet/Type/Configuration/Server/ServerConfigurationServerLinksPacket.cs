using Server.Chat.Text;
using Server.Network.Binary;

namespace Server.Network.Packet.Type.Configuration.Server;

public record ServerConfigurationServerLinksPacket(ServerConfigurationServerLinksPacket.Entry[] Entries) : IServerPacket
{

    public record Entry(KnownServerLinks? KnownServerLink, IComponent? CustomLink, string Url);

    public enum KnownServerLinks
    {
        BugReport = 0,
        CommunityGuidelines = 1,
        Support = 2,
        Status = 3,
        Feedback = 4,
        Community = 5,
        Website = 6,
        Forums = 7,
        News = 8,
        Announcements = 9,
    }
    
    
    public void Write(BinaryBuffer writer)
    {   
        writer.Write(BinaryBuffer.VAR_INT, Entries.Length);
        foreach (var entry in Entries)
        {
            bool isBuiltIn = entry.KnownServerLink != null;
            writer.Write(BinaryBuffer.BOOL, isBuiltIn);
            if (isBuiltIn)
            {
                writer.WriteEnum<KnownServerLinks>(entry.KnownServerLink.Value);
            }
            else
            {
                writer.Write(BinaryBuffer.TEXT_COMPONENT, entry.CustomLink);
            }
            writer.Write(BinaryBuffer.STRING, entry.Url);
        }
    }
}