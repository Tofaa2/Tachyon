using Tachyon.Namespace;

namespace Tachyon.Entity.Type;

public class EntityType
{

    public readonly int Id;
    public readonly NamespaceId Namespace;
    public readonly double Width;
    public readonly double Height;
    
    private EntityType(int id, NamespaceId ns, double width, double height)
    {
        Id = id;
        Namespace = ns;
        Width = width;
        Height = height;
    }

}