using System.Text.Json;

namespace Tachyon.Chat.Text;

public interface ITextComponent : IComponent
{
    
    public ITextComponent Text(string text);
    
}

internal class TextComponent : AbstractComponent, ITextComponent
{

    public static readonly JsonSerializerOptions JsonOptions = new()
    {
        WriteIndented = false,
        PropertyNamingPolicy = JsonNamingPolicy.SnakeCaseLower,
        IgnoreNullValues = true,
        Encoder = System.Text.Encodings.Web.JavaScriptEncoder.UnsafeRelaxedJsonEscaping
    };
    
    public TextComponent(string text)
    {
        _json["text"] = text;
    }

    public TextComponent() : this("") { }

    public TextComponent(string text, TextColor color)
    {
        _style = color;
        _json["text"] = text;
        _json["color"] = color.ToString();
    }
    
    public ITextComponent Text(string text)
    {
        _json["text"] = text;
        return this;
    }
    
}