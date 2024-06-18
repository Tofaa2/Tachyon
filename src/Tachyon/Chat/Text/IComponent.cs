using System.Text.Json.Nodes;
using Tachyon.Util;

namespace Tachyon.Chat.Text;

public interface IComponent
{
    public static readonly IComponent Empty = Text("");
    public static ITextComponent Text(string text) => new TextComponent(text);
    public static ITextComponent Text(string text, TextColor color) => new TextComponent(text, color);

    public static ITranslatableComponent Translatable(string translationKey) => new TranslatableTextComponent(translationKey);
    public static ITranslatableComponent Translatable(string translationKey, IComponent fallback) => new TranslatableTextComponent(translationKey, fallback);

    
    public static IKeybindComponent Keybind(string key) => new KeybindTextComponent(key);
    public static IKeybindComponent Keybind(ClientKeybind key) => new KeybindTextComponent(key);
    
    public IComponent Append(IComponent child);
    
    public IComponent Decoration(TextDecoration decorations);
    
    public IComponent Decorations(params TextDecoration[] decorations);
    
    public bool HasDecoration(TextDecoration decorations);
    
    public IComponent Color(TextColor color);
    
    public string ToJson();
    
    public JsonObject GetRawJson();
    
}