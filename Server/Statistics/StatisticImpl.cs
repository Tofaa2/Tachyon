namespace Server.Statistics;

internal record StatisticImpl(Registry.Registry.StatisticTypeEntry Registry) : IStatisticType
{

    internal static Registry.Registry.Container<IStatisticType> REGISTRY;

}