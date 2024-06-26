using Server.Namespace;
using Server.Registry;

namespace Server.World.Block;

public interface IBlock : IRegistriedStaticProtocolObject<Registry.Registry.BlockEntry>
{
    int IProtocolObject.ProtocolId => Registry.ProtocolId;
    NamespaceId IStaticProtocolObject.Id => Registry.Id;
}