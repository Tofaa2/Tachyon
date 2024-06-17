namespace Tachyon.Network.Connection;


public interface IUniqueIdProvider
{
    
    internal static IUniqueIdProvider Offline => new DefaultUUIDProvider();
    
    Guid Provide(string username, PlayerConnection connection);

}

internal class DefaultUUIDProvider : IUniqueIdProvider
{
    public Guid Provide(string username, PlayerConnection connection)
    {
        return Guid.NewGuid();
    }
}