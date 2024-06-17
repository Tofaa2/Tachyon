using System.Text.Json.Nodes;

namespace Tachyon.Text;

public sealed class TextComponent
{

    private List<TextComponent> _children = new();
    private TextColor? _style;
    private int _decorations = 0;
    private string _text;
    private JsonObject _json = new();
    
    public TextComponent(string text)
    {
        _text = text;
    }

    public TextComponent(string text, TextColor color)
    {
        _text = text;
        _style = color;
        _json["text"] = text;
        _json["color"] = color.ToString();
    }
    
    public TextComponent WithChild(TextComponent child)
    {
        _children.Add(child);
        return this;
    }

    public TextComponent WithDecoration(TextDecoration decoration)
    {
        _decorations |= (int)decoration;
        foreach (var textDecoration in Enum.GetValues<TextDecoration>())
        {
            if (HasDecoration(textDecoration))
            {
                _json[textDecoration.ToString().ToLower()] = true;
            }
        }
        return this;
    }
    
    public bool HasDecoration(TextDecoration decoration)
    {
        return (_decorations & (int)decoration) == (int)decoration;
    }

    public string ToJson()
    {
        if (_children.Count <= 0) return _json.ToJsonString();
        var children = new JsonArray();
        foreach (var child in _children)
        {
            children.Add(child.ToJson());
        }
        _json["extra"] = children;

        return _json.ToJsonString();
    }

}