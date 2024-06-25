using Server.Namespace;

namespace Server.Registry;

public interface IRegistriedStaticProtocolObject<T> : IStaticProtocolObject where T : Registry.IEntry
{
    public T Registry { get; }
}

public interface IStaticProtocolObject : IProtocolObject
{

    public NamespaceId Id { get; }

}

public interface IProtocolObject
{

    public int ProtocolId { get; }

}