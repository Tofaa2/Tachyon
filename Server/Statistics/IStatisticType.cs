using Server.Namespace;
using Server.Registry;
using static Server.Registry.Registry;
namespace Server.Statistics;

public interface IStatisticType : IRegistriedStaticProtocolObject<StatisticTypeEntry>
{

    public static void Init()
    {
        StatisticImpl.REGISTRY = CreateStaticContainer<IStatisticType, StatisticTypeEntry>(
            Resource.STATISTIC_TYPES,
            (namespaceId, json) =>
            {
                return new StatisticImpl(new StatisticTypeEntry(json["id"].GetValue<int>(),
                    NamespaceId.FromString(namespaceId)));
            }
        );
    }
    
    int IProtocolObject.ProtocolId => Registry.ProtocolId;

    NamespaceId IStaticProtocolObject.Id => Registry.Id;
}