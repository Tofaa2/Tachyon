import json
import os
from csharp_file import *


def create_json_content():
    json_data = dict()
    for json in os.listdir("./data"):
        with open(f"./data/{json}", "r") as f:
            data = f.read()
            json_data[json.split(".")[0]] = data
    ns = Namespace("Server")
    csclass = CsClass("MinecraftData")
    csclass.add_modifier(Modifier.PUBLIC)
    csclass.add_modifier(Modifier.STATIC)
    for key, value in json_data.items():
        field = Field()
        field.set_field_name(friendly_name(key))
        field.set_field_type("string")
        field.add_modifier(Modifier.PUBLIC)
        field.add_modifier(Modifier.STATIC)
        field.add_modifier(Modifier.READONLY)
        field.set_field_value(f'"{value}"')
        csclass.add_field(field)
    ns.add_class(csclass)
    file = CsFile("../Server/MinecraftData.cs")
    file.add_namespace(ns)
    file.write_to_file()


def fetch_dir_idiotic(file) -> dict:
    attributes_path = "../Server/Resources/" + file + ".json"

    if not os.path.exists(attributes_path):
        print(f"Error: File {attributes_path} does not exist.")
        return {}

    with open(attributes_path, 'r') as f:
        file_as_string = f.read()
    file_as_string = file_as_string[3:]
    if not file_as_string.strip():
        print(f"Error: File {attributes_path} is empty.")
        return {}

    try:
        attributes = json.loads(file_as_string)
    except json.JSONDecodeError as e:
        print(f"Error decoding JSON: {e}")
        return {}

    return attributes


def fetch_dir(file) -> dict:
    attributes_path = "../Server/Resources/" + file + ".json"

    if not os.path.exists(attributes_path):
        print(f"Error: File {attributes_path} does not exist.")
        return {}

    with open(attributes_path, 'r') as f:
        file_as_string = f.read()
        print(file_as_string)
    if not file_as_string.strip():
        print(f"Error: File {attributes_path} is empty.")
        return {}

    try:
        attributes = json.loads(file_as_string)
    except json.JSONDecodeError as e:
        print(f"Error decoding JSON: {e}")
        return {}

    return attributes


def friendly_name(name: str) -> str:
    split = name.split(":")
    length = len(split)
    if length == 1:
        return split[0].capitalize()
    remaining = split[1].split("_")
    remaining = [x.capitalize() for x in remaining]
    return "".join(remaining)


def generate_constants():
    constants = fetch_dir("constants")
    if not constants:
        print("No constants to display.")
        return

    keys = constants.keys()
    namespace = Namespace("Server")
    cs_class = CsClass("MinecraftConstants")
    cs_class.add_modifier(Modifier.PUBLIC)
    cs_class.add_modifier(Modifier.STATIC)

    nameField = Field()
    nameField.set_field_name("VERSION_NAME")
    nameField.set_field_type("string")
    nameField.set_field_value("\"" + constants["name"] + "\"")

    versionField = Field()
    versionField.set_field_name("PROTOCOL_VERSION")
    versionField.set_field_type("int")
    versionField.set_field_value(constants["protocol"])

    worldField = Field()
    worldField.set_field_name("WORLD_FORMAT_VERSION")
    worldField.set_field_type("int")
    worldField.set_field_value(constants["world"])

    rpField = Field()
    rpField.set_field_name("RESOURCE_PACK_VERSION")
    rpField.set_field_type("int")
    rpField.set_field_value(constants["resourcepack"])

    datapackField = Field()
    datapackField.set_field_name("DATA_PACK_VERSION")
    datapackField.set_field_type("int")
    datapackField.set_field_value(constants["datapack"])

    listOfFields = [nameField, versionField,
                    worldField, rpField, datapackField]

    for field in listOfFields:
        field.add_modifier(Modifier.PUBLIC)
        field.add_modifier(Modifier.STATIC)
        field.add_modifier(Modifier.READONLY)
        cs_class.add_field(field)

    namespace.add_class(cs_class)
    file = CsFile("../Server/MinecraftConstants.cs")
    file.add_namespace(namespace)
    file.write_to_file()


def generate_class(
        namespace: str,
        class_name: str,
        json_file_path: str,
        class_file_path: str,
        field_type: str,
        field_value: str,
        idiotic: bool = False
):
    print(f"Generating class {class_name} in namespace {
          namespace} from {json_file_path} to {class_file_path}.")
    if idiotic:
        asd = fetch_dir_idiotic(json_file_path)
    else:
        asd = fetch_dir(json_file_path)
    if not asd:
        print(f"No {json_file_path} to display.")
        return
    keys = asd.keys()
    namespace = Namespace(namespace)
    cs_class = CsClass(class_name)
    cs_class.add_modifier(Modifier.PUBLIC)
    cs_class.add_modifier(Modifier.STATIC)
    for key in keys:
        field = Field()
        field.set_field_name(friendly_name(key).replace(".", "_"))
        field.set_field_type(field_type)
        field.add_modifier(Modifier.PUBLIC)
        field.add_modifier(Modifier.STATIC)
        field.add_modifier(Modifier.READONLY)
        field.set_field_value(field_value.replace("{key}", key))
        cs_class.add_field(field)
        cs_class.add_modifier(Modifier.PUBLIC)
        cs_class.add_modifier(Modifier.STATIC)
    namespace.add_class(cs_class)
    file = CsFile(class_file_path)
    file.add_namespace(namespace)
    file.write_to_file()


if __name__ == '__main__':
    create_json_content()
    generate_constants()
    generate_class(
        "Server.Attribute",
        "Attributes",
        "attributes",
        "../Server/Attribute/Attributes.cs",
        "IAttribute",
        "AttributeImpl.REGISTRY.Get(\"{key}\")",
        True
    )
    generate_class(
        "Server.Statistics",
        "StatisticTypes",
        "statistics",
        "../Server/Statistics/StatisticTypes.cs",
        "IStatistic",
        "StatisticImpl.REGISTRY.Get(\"{key}\")"
    )
    generate_class(
        "Server.Item.Enchantment",
        "Enchantments",
        "enchantments",
        "../Server/Item/Enchantment/Enchantments.cs",
        "IEnchantment",
        "EnchantmentImpl.REGISTRY.Get(\"{key}\")"
    )
    generate_class(
        "Server.World.Block",
        "Blocks",
        "blocks",
        "../Server/World/Block/Blocks.cs",
        "IBlock",
        "BlockImpl.REGISTRY.Get(\"{key}\")"
    )
    generate_class(
        "Server.Item.Material",
        "Materials",
        "items",
        "../Server/Item/Material/Materials.cs",
        "IMaterial",
        "MaterialImpl.REGISTRY.Get(\"{key}\")"
    )
    generate_class(
        "Server.Item.Armor",
        "TrimMaterials",
        "trim_materials",
        "../Server/Item/Armor/TrimMaterials.cs",
        "ITrimMaterial",
        "TrimMaterialImpl.REGISTRY.Get(\"{key}\")"
    )
