using Server.Chat.Text;
using Server.Namespace;
using Server.Registry;
using static Server.Registry.Registry;
namespace Server.Item.Enchantment;

public interface IEnchantment : IRegistriedStaticProtocolObject<EnchantmentEntry>
{

    public static void Init()
    {
        EnchantmentImpl.REGISTRY = CreateStaticContainer<IEnchantment, EnchantmentEntry>(
            Resource.ENCHANTS,
            (namespaceId, json) =>
            {
                return new EnchantmentImpl(
                    new EnchantmentEntry(
                        NamespaceId.FromString(namespaceId),
                        IComponent.Text(json["description"].GetValue<string>()),
                        json["anvil_cost"].GetValue<int>()
                    ));

            }
        );
    }
    
    
    NamespaceId IStaticProtocolObject.Id => Registry.Id;
    int IProtocolObject.ProtocolId => -1;
}