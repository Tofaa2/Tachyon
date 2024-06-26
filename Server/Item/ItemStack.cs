using Server.Item.Material;

namespace Server.Item;

public interface IItemStack
{
    
    public IMaterial Material { get; }
    
}