namespace Server.Item.Enchantment;

internal record EnchantmentImpl(Registry.Registry.EnchantmentEntry Registry) : IEnchantment
{

    internal static Registry.Registry.Container<IEnchantment> REGISTRY;

}