using Server.Namespace;

namespace Server.Gamedata;

public record DataPack(NamespaceId Namespace)
{
    public bool Synced => false;

}