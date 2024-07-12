using Server.Chat.Text;
using Server.Namespace;
using Server.Nbt;
using Server.Network.Packet.Type.Configuration.Server;
using Server.Registry;


namespace Server.Item.Armor;

public interface ITrimMaterial : IRegistriedStaticProtocolObject<Registry.Registry.TrimMaterialEntry>, INbtSerializable
{

    public static void Init()
    {
        Tachyon.Logger.Info(TrimMaterialImpl.REGISTRY.Namespaces.ToString());
    }
    
    public static ServerConfigurationRegistryPacket CreatePacket()
    {
        List<ServerConfigurationRegistryPacket.Entry> entries = TrimMaterialImpl.REGISTRY.Namespaces.Select(entry => new ServerConfigurationRegistryPacket.Entry(entry.Key, entry.Value.ToNbt())).ToList();
        return new("minecraft:trim_material", entries);
    }
    
    public static ITrimMaterial? FromId(NamespaceId id)
    {
        return TrimMaterialImpl.REGISTRY.Get(id);
    }

    public static ITrimMaterial? FromId(string id)
    {
        return TrimMaterialImpl.REGISTRY.Get(id);
    }
    
    public static ITrimMaterial? FromId(int protocolId)
    {
        return TrimMaterialImpl.REGISTRY.GetId(protocolId);
    }

    public Description TrimDescription => Registry.Description;
    public string AssetName => Registry.AssetName;
    public string Ingredient => Registry.Ingredient;
    public float ItemModelIndex => Registry.ItemModelIndex; 
    NamespaceId IStaticProtocolObject.Id => Registry.Id;

    NbtCompound INbtSerializable.ToNbt()
    {
        NbtCompound nbt = new();
        nbt.Add("asset_name", new NbtString(AssetName));
        nbt.Add("Ingredient", new NbtString(Ingredient));
        nbt.Add("item_model_index", new NbtFloat(ItemModelIndex));
        nbt.Add("override_armor_materials", new NbtCompound());
        nbt.Add("description", new NbtString(IComponent.Text("Test").ToJson()));
        return nbt;
    }

    public record Description(
        string HexColor,
        string TranslationKey
    );

}