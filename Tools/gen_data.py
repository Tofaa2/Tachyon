import json
import os
from csharp_file import *


def fetch_dir(file) -> dict:
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


def friendly_name(name: str) -> str:
    split = name.split(":")
    if len(split) > 1:
        return split[1].upper()

def generate_class(
        namespace: str,
        class_name: str,
        json_file_path: str,
        class_file_path: str,
        field_type: str,
        field_value: str,
):
    asd = fetch_dir(json_file_path)
    if not asd:
        print(f"No {json_file_path} to display.")
        return
    keys = asd.keys()
    namespace = Namespace(namespace)
    cs_class = CsClass(class_name)
    for key in keys:
        field = Field()
        field.set_field_name(friendly_name(key).replace(".", "_"))
        field.set_field_type(field_type)
        field.add_modifier(Modifier.PUBLIC)
        field.add_modifier(Modifier.STATIC)
        field.add_modifier(Modifier.READONLY)
        field.set_field_value(field_value)
        cs_class.add_field(field)
        cs_class.add_modifier(Modifier.PUBLIC)
        cs_class.add_modifier(Modifier.STATIC)
    namespace.add_class(cs_class)
    file = CsFile(class_file_path)
    file.add_namespace(namespace)
    file.write_to_file()


if __name__ == '__main__':
    generate_class(
        "Server.Attribute",
        "Attributes",
        "attributes",
        "../Server/Attribute/Attributes.cs",
        "IAttribute",
        "AttributeImpl.REGISTRY.Get(\"{key}\")",
    )
