using Tachyon.Namespace;
using Tachyon.Util;
using Tachyon.World;

namespace Tachyon.Registry;

public class Registries
{

    public readonly DynamicRegistry<DimensionType> DimensionType = new(NamespaceId.Minecraft("dimension_type")); 
    

    public Registries(IServer server)
    {
        Check.PostInit("Registries Constructor");
        
        DimensionType.AddEntry(NamespaceId.Minecraft("overworld"), World.DimensionType.Overworld );
    }
    
}