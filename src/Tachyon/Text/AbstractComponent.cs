using System.Text.Json;
using System.Text.Json.Nodes;

namespace Tachyon.Text;

internal abstract class AbstractComponent : IComponent
{

    protected List<IComponent> _children = new();
    protected JsonObject _json = new();
    protected JsonArray? _extras;   
    protected TextColor? _style;
    protected int _decorations = 0;
    
    public IComponent Append(IComponent child)
    {
        _children.Add(child);
        if (_extras == null)
        {
            _extras = new JsonArray();
            _json["extra"] = _extras;
        }
        _extras.Add(child.GetRawJson());
        return this;
    }

    public IComponent Decoration(TextDecoration decorations)
    {
        _decorations |= (int)decorations;
        foreach (var textDecoration in Enum.GetValues<TextDecoration>())
        {
            if (HasDecoration(textDecoration))
            {
                _json[textDecoration.ToString().ToLower()] = true;
            }
        }
        return this;    }

    public IComponent Decorations(params TextDecoration[] decorations)
    {
        foreach (var decoration in decorations)
        {
            Decoration(decoration);
        }
        return this;
    }

    public bool HasDecoration(TextDecoration decorations)
    {
        return (_decorations & (int)decorations) == (int)decorations;
    }

    public IComponent Color(TextColor color)
    {
        _style = color;
        _json["color"] = color.ToString();
        return this;
    }

    public string ToJson()
    {
        if (_children.Count == 0)
        {
            return _json.ToJsonString();
        }

        var r = JsonSerializer.Serialize(_json, TextComponent.JsonOptions);
        return r;
    }

    public JsonObject GetRawJson()
    {
        return _json;
    }
}