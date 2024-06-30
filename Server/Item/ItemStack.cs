using Server.Item.Armor;
using Server.Item.Material;

namespace Server.Item;

public interface IItemStack
{
    public IMaterial Material { get; }
    
}