using Tachyon.Chat.Text;

namespace Tachyon.Resourcepack;

public record ResourcePack(Guid Uuid, string Url, string Hash, bool Forced, IComponent? PromptMessage) 
{
    
}