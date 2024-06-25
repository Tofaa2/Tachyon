using Server.Chat.Text;

namespace Server.Resourcepack;

public record ResourcePack(Guid Uuid, string Url, string Hash, bool Forced, IComponent? PromptMessage) 
{
    
}