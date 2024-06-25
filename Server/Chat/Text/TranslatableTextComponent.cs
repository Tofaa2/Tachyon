namespace Server.Chat.Text;

public interface ITranslatableComponent : IComponent
{

    public ITranslatableComponent TranslationKey(string key);

    public ITranslatableComponent Fallback(IComponent fallback);

}


internal sealed class TranslatableTextComponent : AbstractComponent, ITranslatableComponent
{

    internal TranslatableTextComponent(string translation)
    {
        _json["translatable"] = translation;
    }

    internal TranslatableTextComponent(string translation, IComponent fallback) : this(translation)
    {
        _json["fallback"] = fallback.GetRawJson();
    }
    
    public ITranslatableComponent TranslationKey(string key)
    {
        _json["translate"] = key;
        return this;
    }
    
    public ITranslatableComponent Fallback(IComponent fallback)
    {
        _json["fallback"] = fallback.GetRawJson();
        return this;
    }
}