using Tachyon.Namespace;

namespace Tachyon.Registry;

public interface IMappedProtocolEntity : IProtocolEntity
{
    
    public NamespaceId Namespace { get; }
    
    public int Id { get; }
    
}